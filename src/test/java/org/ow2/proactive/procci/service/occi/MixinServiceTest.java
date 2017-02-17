/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.procci.service.occi;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.procci.model.exception.ClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationClientException;
import org.ow2.proactive.procci.model.exception.CloudAutomationServerException;
import org.ow2.proactive.procci.model.occi.infrastructure.Compute;
import org.ow2.proactive.procci.model.occi.infrastructure.ComputeBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Entity;
import org.ow2.proactive.procci.model.occi.metamodel.Mixin;
import org.ow2.proactive.procci.model.occi.metamodel.MixinBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.Resource;
import org.ow2.proactive.procci.model.occi.metamodel.ResourceBuilder;
import org.ow2.proactive.procci.model.occi.metamodel.rendering.MixinRendering;
import org.ow2.proactive.procci.service.CloudAutomationVariablesClient;


/**
 * Created by the Activeeon team on 20/10/16.
 */
public class MixinServiceTest {

    @InjectMocks
    private MixinService mixinService;

    @Mock
    private CloudAutomationVariablesClient cloudAutomationVariablesClient;

    @Mock
    private InstanceService instanceService;

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getMixinByTitleTest() {

        String mixinTitle = "getMixin";
        Entity entity = new ResourceBuilder().url("entityTest").build();

        Mixin mixinToReturn = new MixinBuilder("schemeTest", "termTest").addEntity(entity).title(mixinTitle).build();

        when(cloudAutomationVariablesClient.get(mixinTitle)).thenReturn(MixinRendering.convertStringFromMixin(mixinToReturn.getRendering()));
        when(instanceService.getMixinsFreeEntity(entity.getId())).thenReturn(Optional.of(entity));

        Mixin mixinGot = mixinService.getMixinByTitle(mixinTitle);

        verify(cloudAutomationVariablesClient).get(mixinTitle);
        verify(instanceService).getMixinsFreeEntity(entity.getId());

        assertThat(mixinGot).isNotNull();
        assertThat(mixinGot.getTitle()).isEqualTo(mixinTitle);
        assertThat(mixinGot.getEntities()).containsExactly(entity);
    }

    @Test
    public void getMixinMockByTitleTest() {

        String mixinTitle = "getMixin";
        Entity entity = new ResourceBuilder().url("entityTest").build();

        Mixin mixinToReturn = new MixinBuilder("schemeTest", "termTest").title(mixinTitle).addEntity(entity).build();

        when(cloudAutomationVariablesClient.get(mixinTitle)).thenReturn(MixinRendering.convertStringFromMixin(mixinToReturn.getRendering()));
        when(instanceService.getMixinsFreeEntity(entity.getId())).thenReturn(Optional.of(entity));

        Mixin mixinGot = mixinService.getEntitiesFreeMixinByTitle(mixinTitle);

        verify(cloudAutomationVariablesClient).get(mixinTitle);
        verify(instanceService).getMixinsFreeEntity(entity.getId());

        assertThat(mixinGot).isNotNull();
        assertThat(mixinGot.getTitle()).isEqualTo(mixinTitle);
        assertThat(mixinGot.getEntities()).isEmpty();
    }

    @Test
    public void getMixinsByEntityIdTest() throws IOException {

        String entityId = "entity";

        Mixin mixin1FromEntity = new MixinBuilder("mixinFromEntity", "test1").build();
        Mixin mixin2FromEntity = new MixinBuilder("mixinFromEntity", "test2").build();

        Set<String> mixinsFromEntity = new HashSet<>();
        mixinsFromEntity.add(mixin1FromEntity.getTitle());
        mixinsFromEntity.add(mixin2FromEntity.getTitle());

        when(cloudAutomationVariablesClient.get(entityId)).thenReturn(mapper.writeValueAsString(mixinsFromEntity));
        when(cloudAutomationVariablesClient.get(mixin1FromEntity.getTitle())).thenReturn(mapper.writeValueAsString(mixin1FromEntity.getRendering()));
        when(cloudAutomationVariablesClient.get(mixin2FromEntity.getTitle())).thenReturn(mapper.writeValueAsString(mixin2FromEntity.getRendering()));

        List<Mixin> mixinsGot = mixinService.getMixinsByEntityId(entityId);

        assertThat(mixinsGot).containsExactly(mixin1FromEntity, mixin2FromEntity);

        verify(cloudAutomationVariablesClient).get(entityId);
        verify(cloudAutomationVariablesClient).get(mixin1FromEntity.getTitle());
        verify(cloudAutomationVariablesClient).get(mixin2FromEntity.getTitle());

    }

    @Test
    public void getEntityMixinNamesTest() throws IOException {
        Set<String> references = new HashSet<>();
        references.add("ref1Test");
        references.add("ref2Test");
        when(cloudAutomationVariablesClient.get("idTest")).thenReturn(mapper.writeValueAsString(references));
        Set<String> result = mixinService.getMixinNamesFromEntity("idTest");
        assertThat(result).contains("ref1Test");
        assertThat(result).contains("ref2Test");

        when(cloudAutomationVariablesClient.get("idTest2")).thenReturn("[]");
        references = mixinService.getMixinNamesFromEntity("idTest2");
        assertThat(references).isEmpty();

        when(cloudAutomationVariablesClient.get("idTest3")).thenThrow(new CloudAutomationServerException("idTest3",
                                                                                                         "url",
                                                                                                         "content"));

        Exception ex = null;
        try {
            mixinService.getMixinNamesFromEntity("idTest3");
        } catch (Exception e) {
            ex = e;
        }

        assertThat(ex).isInstanceOf(CloudAutomationServerException.class);
    }

    @Test
    public void addReferenceTest() throws IOException {

        //test update object and update mixin

        Set<String> mixinsId = new HashSet<>();
        mixinsId.add("mixinTest");

        Mixin mixin = new MixinBuilder("schemeTest", "mixinTest").build();

        Compute compute = new ComputeBuilder().url("idTest").addMixin(mixin).build();

        when(cloudAutomationVariablesClient.get("mixinTest")).thenReturn(mapper.writeValueAsString(mixin.getRendering()));

        mixinService.addEntity(compute);

        mixin.addEntity(compute);

        verify(cloudAutomationVariablesClient).post("idTest", mapper.writeValueAsString(mixinsId));
        verify(cloudAutomationVariablesClient).get("mixinTest");
        verify(cloudAutomationVariablesClient).update("mixinTest", mapper.writeValueAsString(mixin.getRendering()));

        //test add new object with new mixin

        Set<String> mixinId2 = new HashSet<>();
        mixinId2.add("mixinTest2");

        Mixin mixin2 = new MixinBuilder("schemeTest2", "mixinTest2").build();

        Compute compute2 = new ComputeBuilder().url("idTest2").addMixin(mixin2).build();

        mixin2.addEntity(compute2);

        when(cloudAutomationVariablesClient.get("idTest2")).thenReturn(mapper.writeValueAsString(mixin.getRendering()));

        Mockito.doThrow(new CloudAutomationClientException("mixinTest2 not found"))
               .when(cloudAutomationVariablesClient)
               .get("mixinTest2");

        mixinService.addEntity(compute2);

        verify(cloudAutomationVariablesClient).post("idTest2", mapper.writeValueAsString(mixinId2));
        verify(cloudAutomationVariablesClient).get("mixinTest2");
        verify(cloudAutomationVariablesClient).post("mixinTest2", mapper.writeValueAsString(mixin2.getRendering()));

    }

    @Test
    public void removeMixinTest() throws IOException {

        Mixin mixin1 = new MixinBuilder("mixinTest", "toKeep1").build();
        Mixin mixin2 = new MixinBuilder("mixinTest", "toKeep2").build();
        Set<String> notDeletedMixins = new HashSet<>();
        Set<String> allMixins = new HashSet<>();
        Set<String> setWithMixinToRemove = new HashSet<>();
        notDeletedMixins.add(mixin1.getTitle());
        notDeletedMixins.add(mixin2.getTitle());
        allMixins.addAll(notDeletedMixins);

        Resource resourceWithThreeMixin = new ResourceBuilder().url("resourceWithThreeMixin").build();
        Resource resourceWithTheMixinToRemove = new ResourceBuilder().url("resourceWithTheMixinToRemove").build();

        Mixin mixinToRemove = new MixinBuilder("mixinTest", "toRemove").addEntity(resourceWithTheMixinToRemove)
                                                                       .addEntity(resourceWithThreeMixin)
                                                                       .build();

        allMixins.add(mixinToRemove.getTitle());
        setWithMixinToRemove.add(mixinToRemove.getTitle());

        when(cloudAutomationVariablesClient.get(mixinToRemove.getTitle())).thenReturn(MixinRendering.convertStringFromMixin(mixinToRemove.getRendering()));

        when(instanceService.getMixinsFreeEntity(resourceWithThreeMixin.getId())).thenReturn(Optional.of(resourceWithThreeMixin));

        when(instanceService.getMixinsFreeEntity(resourceWithTheMixinToRemove.getId())).thenReturn(Optional.of(resourceWithTheMixinToRemove));

        when(cloudAutomationVariablesClient.get(resourceWithThreeMixin.getId())).thenReturn(mapper.writeValueAsString(allMixins));

        when(cloudAutomationVariablesClient.get(resourceWithTheMixinToRemove.getId())).thenReturn(mapper.writeValueAsString(setWithMixinToRemove));

        mixinService.removeMixin(mixinToRemove.getTitle());

        verify(cloudAutomationVariablesClient).delete(mixinToRemove.getTitle());
        verify(cloudAutomationVariablesClient).update(resourceWithThreeMixin.getId(),
                                                      mapper.writeValueAsString(notDeletedMixins));
        verify(cloudAutomationVariablesClient).update(resourceWithTheMixinToRemove.getId(),
                                                      mapper.writeValueAsString(new HashSet<String>()));
    }

    @Test
    public void removeNotFoundMixinTest() throws IOException {

        String mixinName = "unexistingMixin";
        Exception ex = null;

        when(cloudAutomationVariablesClient.get(mixinName)).thenThrow(new CloudAutomationClientException(mixinName +
                                                                                                         " test"));
        try {
            mixinService.removeMixin(mixinName);
        } catch (Exception e) {
            ex = e;
        }

        assertThat(ex).isInstanceOf(CloudAutomationClientException.class);
    }

    @Test
    public void deleteEntityTest() throws IOException {

        String entityIdToDelete = "entityIdToDelete";
        String oneEntityMixinToUpdateTitle = "oneEntityMixinToUpdate";
        String severalEntitiesMixinToUpdateTitle = "severalEntitiesMixinToUpdate";

        Entity mockEntity = new ResourceBuilder().build();

        Entity entity = new ResourceBuilder().url(entityIdToDelete).build();

        Mixin oneEntityMixinToUpdate = new MixinBuilder(oneEntityMixinToUpdateTitle, "term").addEntity(entity).build();

        Mixin severalEntitiesMixinToUpdate = new MixinBuilder(severalEntitiesMixinToUpdateTitle, "term")
                                                                                                        .addEntity(entity)
                                                                                                        .addEntity(mockEntity)
                                                                                                        .build();

        Set<String> mixinToUpdateId = new HashSet<>();
        mixinToUpdateId.add(oneEntityMixinToUpdateTitle);
        mixinToUpdateId.add(severalEntitiesMixinToUpdateTitle);

        when(cloudAutomationVariablesClient.get(entityIdToDelete)).thenReturn(mapper.writeValueAsString(mixinToUpdateId));
        when(cloudAutomationVariablesClient.get(oneEntityMixinToUpdateTitle)).thenReturn(mapper.writeValueAsString(oneEntityMixinToUpdate.getRendering()));
        when(cloudAutomationVariablesClient.get(severalEntitiesMixinToUpdateTitle)).thenReturn(mapper.writeValueAsString(severalEntitiesMixinToUpdate.getRendering()));
        when(instanceService.getMixinsFreeEntity(mockEntity.getId())).thenReturn(Optional.of(mockEntity));
        when(instanceService.getMixinsFreeEntity(entity.getId())).thenReturn(Optional.of(entity));

        mixinService.deleteEntity(entityIdToDelete);

        verify(cloudAutomationVariablesClient).delete(entityIdToDelete);
        verify(cloudAutomationVariablesClient).get(oneEntityMixinToUpdateTitle);
        verify(cloudAutomationVariablesClient).get(severalEntitiesMixinToUpdateTitle);
        verify(instanceService, times(2)).getMixinsFreeEntity(entity.getId());
        verify(instanceService, times(1)).getMixinsFreeEntity(mockEntity.getId());
    }

    @Test
    public void failEntityDeletion() {

        String wrongEntityId = "wrongEntityId";

        when(cloudAutomationVariablesClient.get(wrongEntityId)).thenThrow(new CloudAutomationClientException("not found"));
        Exception ex = null;
        try {
            mixinService.deleteEntity(wrongEntityId);
        } catch (Exception e) {
            ex = e;
        }

        assertThat(ex).isNotNull();
        assertThat(ex).isInstanceOf(CloudAutomationClientException.class);

    }

}
