# OCCI Proactive Cloud Automation for infrastructure

OCCI Proactive Cloud Automation deployment ensure services for Infrastructure, which are the services responsible of resource provisioning at the infrastructure level regarding virtual machines, operating systems, network,storage.

## List informations of all deployed compute

```curl -X GET --header 'Accept: application/json' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/compute/'```


## Display informations of a specific compute

```curl -X GET --header 'Accept: application/json' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/compute/urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29'```


## Create a compute

### Full curl request :

<pre>
<code>
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
  "actions": [],
    "attributes": {
      "occi.compute.memory":3.0,
      "occi.compute.cores":4,
      "occi.compute.architecture":"x64",
      "occi.entity.title":"vm_name",
      “occi.core.summary”:”creation example”,
      “occi.compute.state”:”ACTIVE"
},
  "id": "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29",
  "kind": "http://schemas.ogf.org/occi/infrastructure#compute",
  "links": [],
  "mixins": []
}' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/compute/'
</pre>
</code>

### Short form curl request :

<pre>
<code>
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
  "attributes": {
  "occi.compute.memory":3.0,
  "occi.compute.cores":4,
  "occi.compute.architecture":"x64",
  "occi.entity.title":"vm_name"
},
  "id": "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29",
  "kind": "http://schemas.ogf.org/occi/infrastructure#compute"
}' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/compute/'
</pre>
</code>


## Create a user-defined Mixin

<pre>
<code>
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
{
    "scheme": "my.mixin",
      "term": "mine"
}
</pre>
</code>


##Use a provider mixin during compute creation

Create a compute (VM instance) from an image with “imagename” is the image ID and “occi.entity.title” the VM name.

<pre>
<code>
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
    "attributes": {
        "occi.entity.title": "vm_name",
        "vmimage": {
            "imagename": "e3161161-02a4-4685-ad99-8ac36b3e66ea",
            "occi.category.title": "ubuntuMixin"
        },
        "user_data": {
            "occi.compute.userdata": "touch /tmp/replace_command_by_script",
            "occi.category.title": "scriptMixin"
        }
    }
}' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/compute/'
</pre>
</code>

This request creates : 
 - the mixin vmimage with "ubuntuMixin" as name
 - the mixin scriptMixin with "scriptMixin" as name
 - the compute vm_name with the mixins ubuntuMixin and scriptMixin


Provider Mixin available : 
 - Vmimage -> enable to deploy a specific image
 - Userdata -> enable to deploy a script on the instance 
