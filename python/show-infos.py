import aerospike
import re
# Aerospike 클러스터에 연결
config = {
    'hosts': [('127.0.0.1', 3000)]  # 호스트 및 포트 번호를 알맞게 설정해야 합니다.
}
client = aerospike.client(config).connect()

# Aerospike 클러스터에서 모든 네임스페이스(namespace) 가져오기
namespaces = client.info('namespaces')
print(namespaces)

client = aerospike.client(config).connect()
sets = client.info("sets")
for namespace in sets :
    string = sets[namespace][1]
    pattern = r'test:set=(\d+:\d+)'
    print(string)
    matches = re.findall(pattern, string)
    for s in matches :
        print(s)

# Aerospike 클라이언트 연결 닫기
client.close()