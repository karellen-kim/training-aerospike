import com.aerospike.client.AerospikeClient
import com.aerospike.client.Bin
import com.aerospike.client.Key
import io.github.oshai.kotlinlogging.KotlinLogging

fun main(args: Array<String>) {

    val logger = KotlinLogging.logger {}

    AerospikeClient("127.0.0.1", 3000).use { client ->
        val key = Key("test", "demo", "putgetkey")
        val bin1 = Bin("bin1", "value1")
        val bin2 = Bin("bin2", "value2")

        client.put(null, key, bin1, bin2)
        val record = client.get(null, key)
        logger.info(record.toString())
    }

}