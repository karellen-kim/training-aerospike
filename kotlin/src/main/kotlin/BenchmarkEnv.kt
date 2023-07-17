import com.aerospike.client.Key

object BenchmarkEnv {
    val HOST = "127.0.0.1"
    // Maximum bins per record: 32,767 bins. 100000 에러 왜 나지?
    val keySize = 6000
    val binIds = (1 .. keySize).toList().map { it.toString() }
    val key = Key("test", "test", 0)
}