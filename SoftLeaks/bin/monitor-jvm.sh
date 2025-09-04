# monitor-jvm.sh - Real-time JVM monitoring

#cat > monitor-jvm.sh << 'EOF'
#!/bin/bash

echo "JVM Process Monitor"
echo "=================="

# Find Java processes
echo "Java processes:"
jps -l

read -p "Enter PID to monitor: " pid

if [ -z "$pid" ]; then
    echo "No PID provided"
    exit 1
fi

echo "Monitoring PID: $pid"
echo "Press Ctrl+C to stop"

# Create monitoring loop
while true; do
    clear
    echo "JVM Monitor - PID: $pid - $(date)"
    echo "=================================="
    
    # GC information
    echo "GC Information:"
    jstat -gc $pid
    
    echo ""
    echo "Memory Usage:"
    jstat -gccapacity $pid
    
    echo ""
    echo "GC Performance:"
    jstat -gcutil $pid
    
    sleep 2
done
EOF

chmod +x monitor-jvm.sh

