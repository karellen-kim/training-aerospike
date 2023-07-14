import aerospike

# Aerospike 클러스터에 연결
config = {
    'hosts': [('127.0.0.1', 3000)]  # 호스트 및 포트 번호를 알맞게 설정해야 합니다.
}
client = aerospike.client(config).connect()

sets = [
    "0:0",
]

try:
    for s in sets :
        client.index_remove("test", s)
    print("세트 삭제 성공")
except aerospike.exception.AerospikeError as e:
    print(f"세트 삭제 실패: {e}")

# 연결 닫기
client.close()