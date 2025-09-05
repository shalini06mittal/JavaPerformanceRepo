package org.sample;


import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * mvn clean package
java -Djmh.histogram=true -Djmh.histogram.output=./histograms  -jar target/benchmarks.jar MultiModeBenchmark -f 1

How to Analyze

Throughput → How many operations per ms. Best for system capacity.

AverageTime → Mean latency per op. Good for comparing implementations.

SampleTime → Adds tail latency (p90/p99). Crucial for SLA analysis.

SingleShotTime → Cold start / big batch tasks.
 */


@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class MultiModeBenchmark {

    // Parameterized input size
    @Param({"100", "1000"})
    int size;

    // Workload: sum of integers
    private int workload() {
        return IntStream.range(1, size).sum();
    }

    // 1. Throughput mode (ops per second/millisecond)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public int testThroughput() {
        return workload();
    }

    // 2. Average time per operation
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int testAverageTime() {
        return workload();
    }

    // 3. Sample time (with percentiles + histogram enabled)
    @Benchmark
    @BenchmarkMode(Mode.SampleTime)
    @Warmup(iterations = 2)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    public int testSampleTime() {
        return workload();
    }

    //  4. Single shot (cold start measurement)
    @Benchmark
    @BenchmarkMode(Mode.SingleShotTime)
    @Warmup(iterations = 0)   // important for single shot
    @Measurement(iterations = 1)
    @Fork(1)
    public int testSingleShot() {
        return workload();
    }
}
