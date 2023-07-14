import com.aerospike.client.AerospikeClient
import com.aerospike.client.Bin
import com.aerospike.client.Key
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WriteTest {
    val logger = KotlinLogging.logger {}
    lateinit var client: AerospikeClient

    @Test
    fun `데이터를 추가할 수 있다`() {
        val key = Key("test", "demo", "putgetkey")
        val bin1 = Bin("bin1", "value1")
        val bin2 = Bin("bin2", "value2")

        client.put(null, key, bin1, bin2)
        val record = client.get(null, key)
        logger.info(record.toString())
    }

    @Test
    fun `ttl을 설정할 수 있다`() {
        val key = Key("test", "demo", "putgetkey")
        val bin1 = Bin("bin1", "value1")
        val bin2 = Bin("bin2", "value2")

        client.put(null, key, bin1, bin2)
        val record = client.get(null, key)
        logger.info(record.toString())
    }

    @BeforeEach
    fun before() {
        client = AerospikeClient("127.0.0.1", 3000)
    }

    @AfterEach()
    fun after() {
        client.close()
    }
}