# OCCI Proactive Cloud Automation for BigData

OCCI Proactive Cloud Automation deployment ensure services for deploying spark as a service.

## List the information of all deployed swarm

```curl -X GET --header 'Accept: application/json' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/swarm/'```


## Describes the information of a deployed swarm

```curl -X GET --header 'Accept: application/json' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/swarm/urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29'```

## Create a compute

### Curl complete request :

<pre>
<code>
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{
  "actions": [],
    "attributes": {
      "pca.platform.swarm.machineName":"myMachine",
      "pca.platform.swarm.hostIP":"xx.xxx.xx.xxx",
      "pca.platform.swarm.masterIP":"yy.yyy.yy.yyy",
      "pca.platform.swarm.agentIP":"aa.aaa.aa.aaa, bb.bbb.bb.bbb",
      "pca.platform.swarm.networkName":"my-net",
      "occi.entity.title":"swarm_name",
      "occi.core.summary":"creation example",
      "occi.platform.status":"ACTIVE"
},
  "id": "urn:uuid:996ad860−2a9a−504f−886−aeafd0b2ae29",
  "kind": "http://schema.activeeon.org/occi/platform#swarm",
  "links": [],
  "mixins": []
}' 'http://xx.xxx.xx.xxx:8080/multi-language-connector/occi/swarm/'
</pre>
</code>
