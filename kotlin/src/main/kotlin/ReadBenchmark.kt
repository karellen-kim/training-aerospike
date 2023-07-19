package test

import com.aerospike.client.AerospikeClient
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
    iterations = 10, // 측정 반복 횟수
    time = 1,
    timeUnit = TimeUnit.SECONDS)
class ReadBenchmark {
    val logger = KotlinLogging.logger {}
    var shuffledKeys = BenchmarkEnv.binIds

    lateinit var client: AerospikeClient

    @Benchmark
    fun readOne(): MutableMap<String, Any>? {
        val record = client.get(null, BenchmarkEnv.key, shuffledKeys.first())

        return record.bins
    }

    @Benchmark
    fun read10Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(10).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read100Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(100).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read200Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(200).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read400Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(400).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read600Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(600).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read800Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(800).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read1000Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(1000).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun read2000Keys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(2000).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun readHalfKeys(): MutableMap<String, Any>? {
        val ids = shuffledKeys.take(BenchmarkEnv.keySize/2).toTypedArray()

        val record = client.get(null, BenchmarkEnv.key, *ids)
        return record.bins
    }

    @Benchmark
    fun readAll(): MutableMap<String, Any>? {
        val record = client.get(null, BenchmarkEnv.key)
        return record.bins
    }

    @Setup
    fun before() {
        client = AerospikeClient(BenchmarkEnv.HOST, 3000)
    }

    @TearDown
    fun after() {
        client.close()
    }

    /**
     * Trial : 벤치마크를 실행할 때 마다 한번씩 호출하며, 실행은 전체 Fork를 의미한다.
     * Iteration : 벤치마크를 반복 할 때마다 한번씩 호출
     * Invocation : 벤치마크를 메소드를 호출 할 때마다 호출
     */
    @Setup(Level.Invocation)
    fun EachIteration() {
        shuffledKeys = BenchmarkEnv.binIds.shuffled()
    }
}