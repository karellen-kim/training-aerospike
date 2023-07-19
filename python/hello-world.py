import aerospike
import os
from dotenv import load_dotenv

load_dotenv()
host = os.getenv("HOST")
namespace = os.getenv("NAMESPACE")

config = {
  'hosts': [ (host, 3000) ]
}

try:
  client = aerospike.client(config).connect()

  # (namespace, set, user defined key)
  key = (namespace, 'demo', 'foo')
  
  client.put(key, {'name': 'John Doe', 'age': 32, 'greeting': 'Hello, World!' })
  (key, metadata, record) = client.get(key)
  print("Metadata is", metadata)
  print("Record contents are", record)

  (key, metadata, record) = client.select(key, ["name", "age"])
  print("Metadata is", metadata)
  print("Record contents are", record)

  client.close()
except Exception as e :
  print(e.msg)