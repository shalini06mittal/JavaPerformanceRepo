package org.sample;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

/**
 * -Djmh.histogram=true → enables .hgrm histogram files.

-bm sample → forces SampleTime mode.

-tu us → results in microseconds.

-f 1 → 1 fork (JVM instance).

-wi 2 → 2 warmup iterations.

-i 5 → 5 measurement iterations.
 */
//java -Djmh.histogram=true -Djmh.histogram.output=./histograms -jar target/benchmarks.jar SamplePercentileBenchmark -bm sample -tu us -f 1 -wi 2 -i 5

@State(Scope.Thread)
@BenchmarkMode(Mode.SampleTime)  // Sample mode for histogram
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class SamplePercentileBenchmark {

    @Benchmark
    @Warmup(iterations = 2)
    @Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
    @Fork(1)
    public int testSampleTime() {
        // Simulate some work (~10µs)
        return IntStream.range(1, 1000).sum();
    }
}
