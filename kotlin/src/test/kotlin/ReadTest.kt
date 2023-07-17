import com.aerospike.client.AerospikeClient
import com.aerospike.client.Bin
import com.aerospike.client.Key
import com.aerospike.client.policy.WritePolicy
import io.github.oshai.kotlinlogging.KotlinLogging
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class ReadTest {
    val logger = KotlinLogging.logger {}
    lateinit var client: AerospikeClient

    @Test
    fun `레코드의 존재 여부를 확인한다`() {
        val key = Key("test", "data", 1)
        insertMockData(key)

        val exists: Boolean = client.exists(null, key)
        assertTrue(exists)
    }

    @Test
    fun `메타 정보만 읽는다`() {
        val key = Key("test", "data", 1)
        insertMockData(key)

        val record = client.getHeader(null, key)
        logger.info(record.toString())
        assertNotNull(record)
    }

    @Test
    fun `전체 데이터를 읽는다`() {
        val key = Key("test", "data", 1)
        insertMockData(key)

        val record = client.get(null, key)
        logger.info(record.toString())
        assertNotNull(record)
    }

    @Test
    fun `특정 데이터를 읽는다`() {
        val key = Key("test", "data", 1)
        insertMockData(key)

        val record = client.get(null, key,
            "occurred",
            "report",
            "location" /* 없는 필드 */
        )
        logger.info(record.toString())
        assertNotNull(record)
    }

    fun insertMockData(key: Key) {
        val writePolicy = WritePolicy()
        writePolicy.sendKey = true

        val occurred = Bin("occurred", 20220531)
        val reported = Bin("reported", 20220601)
        val posted = Bin("posted", 20220601)
        val report: Bin = Bin("report", mapOf(
            "shape" to listOf(1, 2, 3),
            "summary" to "summary",
            "city" to "gangnam",
            "state" to "seoul",
            "duration" to "24h"
        ))
        client.put(writePolicy, key, occurred, reported, posted, report)
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