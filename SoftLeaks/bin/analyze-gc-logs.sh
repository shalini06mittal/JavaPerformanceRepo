# GC Log Analysis Script

#!/bin/bash

echo "GC Log Analyzer"
echo "==============="

if [ ! -d "gc-logs" ]; then
    echo "No gc-logs directory found. Run the demo first."
    exit 1
fi

echo "Available log files:"
ls -la gc-logs/

echo ""
read -p "Enter log file name to analyze: " logfile

if [ ! -f "gc-logs/$logfile" ]; then
    echo "Log file not found: gc-logs/$logfile"
    exit 1
fi

echo "Analyzing: gc-logs/$logfile"
echo "=========================="

# Basic GC statistics
echo "GC Event Summary:"
echo "-----------------"
grep -E "(GC|Full GC)" gc-logs/$logfile | wc -l | xargs echo "Total GC events:"
grep "Full GC" gc-logs/$logfile | wc -l | xargs echo "Full GC events:"
grep -E "GC.*[0-9]+\.[0-9]+ms" gc-logs/$logfile | wc -l | xargs echo "Minor GC events:"

echo ""
echo "Memory Usage Patterns:"
echo "----------------------"
# Extract heap usage before and after GC
grep -oE "[0-9]+K->[0-9]+K" gc-logs/$logfile | head -10

echo ""
echo "GC Pause Times:"
echo "---------------"
# Extract pause times
grep -oE "[0-9]+\.[0-9]+ms" gc-logs/$logfile | head -10

echo ""
echo "Longest Pause Times:"
echo "--------------------"
grep -oE "[0-9]+\.[0-9]+ms" gc-logs/$logfile | sort -nr | head -5

echo ""
echo "Average Pause Time:"
echo "-------------------"
grep -oE "[0-9]+\.[0-9]+ms" gc-logs/$logfile | \
    sed 's/ms//' | \
    awk '{sum+=$1; count++} END {if(count>0) printf "%.2f ms\n", sum/count}'

EOF

chmod +x analyze-gc-logs.sh

echo "Scripts created successfully!"
echo ""
echo "Usage:"
echo "1. ./run-gc-demo.sh - Run the GC demo with different collectors"
echo "2. ./monitor-jvm.sh - Monitor a running JVM process"
echo "3. ./analyze-gc-logs.sh - Analyze generated GC logs"
