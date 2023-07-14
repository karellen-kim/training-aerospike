import com.aerospike.client.AerospikeClient
import com.aerospike.client.Bin
import com.aerospike.client.Key
import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.common.runBlocking
import io.kotest.property.Arb
import io.kotest.property.arbitrary.int
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DataTypeTest {
    val logger = KotlinLogging.logger {}
    lateinit var client: AerospikeClient

    @Test
    fun `int를 변환할 수 있다`() {
        val key = Key("test", "demo", "putgetkey")

        runBlocking {
            val bins = Arb.int()
                .samples()
                .mapIndexed { index, v ->
                    Bin(index.toString(), v.value)
                }
                .take(10)
                .toList()

            client.put(null, key, *bins.toTypedArray())
            val record = client.get(null, key)
            print(record)
        }

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