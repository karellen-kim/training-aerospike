package test

import com.aerospike.client.AerospikeClient
import com.aerospike.client.Bin
import com.aerospike.client.policy.WritePolicy
import io.github.oshai.kotlinlogging.KotlinLogging
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.*

/**
 * gradle task : benchmark로 실행
 */

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 0)
/**
 * Throughput : 초당 작업수 측정, 기본값
 * AverageTime : 작업이 수행되는 평균 시간을 측정
 * SampleTime : 최대, 최소 시간 등 작업이 수행하는데 걸리는 시간을 측정
 * SingleShotTime : 단일 작업 수행 소요 시간 측정, Cold Start(JVM 예열 없이) 수행하는데 적격
 * All : 위 모든 시간을 측정
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Measurement(
    iterations = 5, // 측정 반복 횟수
    time = 1,
    timeUnit = TimeUnit.SECONDS)
class WriteBenchmark {
    val logger = KotlinLogging.logger {}
    lateinit var client: AerospikeClient

    //@Benchmark
    fun write() {
        val writePolicy = WritePolicy()
        writePolicy.sendKey = true
        writePolicy.expiration = 60 * 60 * 60
        val bins = BenchmarkEnv.binIds.map { num ->
            Bin(num, num)
        }.toTypedArray()

        client.put(writePolicy, BenchmarkEnv.key, *bins)
    }

    @Setup
    fun before() {
        client = AerospikeClient("127.0.0.1", 3000)
    }

    @TearDown
    fun after() {
        client.close()
    }

}