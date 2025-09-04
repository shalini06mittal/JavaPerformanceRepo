#!/bin/bash

# export CLASSPATH="/Users/Shalini/Desktop/JavaPerformanceWS/SoftLeaks/bin"

# run-gc-demo.sh - Script to run the GC monitoring demo with various GC collectors
function run_serial_gc() {
    echo "Running with Serial GC..."
    java -Xms256m -Xmx512m \
         -XX:+UseSerialGC \
         -Xlog:gc*:verbose_gc.log:time \
          main.GCMonitoringDemo
}

function run_parallel_gc() {
    echo "Running with Parallel GC..."
    java -Xms256m -Xmx512m \
         -XX:+UseParallelGC \
         -Xlog:gc*:verbose_gc.log:time \
         main.GCMonitoringDemo
}

function run_g1_gc() {
    echo "Running with G1 GC..."
    java -Xms256m -Xmx512m \
         -XX:+UseG1GC \
         -XX:MaxGCPauseMillis=100 \
         -Xlog:gc*:verbose_gc.log:time \
         main.GCMonitoringDemo
}

function run_zgc() {
    echo "Running with ZGC (requires Java 11+)..."
    java -Xms256m -Xmx512m \
         -XX:+UseZGC \
         -Xlog:gc*:verbose_gc.log:time \
         main.GCMonitoringDemo
}

function run_all_collectors() {
    echo "Running all collectors for comparison..."
    run_serial_gc
    sleep 2
    run_parallel_gc
    sleep 2
    run_g1_gc
    sleep 2
    
    # Check Java version for ZGC support
    java_version=$(java -version 2>&1 | grep -oP 'version "1\.\d+\.\d+"' | grep -oP '\d+' | head -1)
    if [ "$java_version" -ge 11 ]; then
        run_zgc
    else
        echo "Skipping ZGC - requires Java 11+"
    fi
    
    echo ""
    echo "All tests completed. Log files are in gc-logs/ directory"
    echo "Use analyze-gc-logs.sh to analyze the results"
}

echo "GC Monitoring Demo Runner"
echo "========================="

# Compile the Java class
echo "Compiling GCMonitoringDemo.java..."
# javac GCMonitoringDemo.java

# if [ $? -ne 0 ]; then
#     echo "Compilation failed!"
#     exit 1
# fi

# Create logs directory
mkdir -p gc-logs

echo ""
echo "Select GC collector to test:"
echo "1. Serial GC (-XX:+UseSerialGC)"
echo "2. Parallel GC (-XX:+UseParallelGC) [Default in Java 8]"
echo "3. G1 GC (-XX:+UseG1GC) [Default in Java 9+]"
echo "4. ZGC (-XX:+UseZGC) [Java 11+]"
echo "5. Run all collectors for comparison"

read -p "Enter choice (1-5): " choice

case $choice in
    1)
        run_serial_gc
        ;;
    2)
        run_parallel_gc
        ;;
    3)
        run_g1_gc
        ;;
    4)
        run_zgc
        ;;
    5)
        run_all_collectors
        ;;
    *)
        echo "Invalid choice"
        exit 1
        ;;
esac


