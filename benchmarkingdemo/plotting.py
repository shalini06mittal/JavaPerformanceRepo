import matplotlib.pyplot as plt

latencies = []
counts = []

with open("SamplePercentileBenchmark-testSampleTime.hgrm") as f:
    for line in f:
        if line.strip() and not line.startswith("#") and not line.startswith("Value"):
            parts = line.split()
            value = float(parts[0])   # latency
            percentile = float(parts[1]) * 100
            latencies.append(value)
            counts.append(percentile)

plt.plot(counts, latencies)
plt.xlabel("Percentile (%)")
plt.ylabel("Latency (µs)")
plt.title("Latency Histogram from JMH HDR Data")
plt.grid(True)
plt.show()
