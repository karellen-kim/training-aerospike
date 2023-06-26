import aerospike

config = {
  'hosts': [ ('127.0.0.1', 3000) ]
}

try:
  client = aerospike.client(config).connect()

  key = ('test', 'demo', 'foo')
  print('Working with record key ', key)

  client.put(key, {'name': 'John Doe', 'age': 32, 'greeting': 'Hello, World!' })
  (key, metadata, record) = client.get(key)

  print("Key's components are", key)
  print("Metadata is", metadata)
  print("Record contents are", record)

  client.close()
except:
  import sys
  print("Failed to connect to the cluster with", config['hosts'])
  sys.exit(1)