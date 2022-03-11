```
mvn install
```

```
python3 -m grpc_tools.protoc -Iproto --python_out=client --grpc_python_out=client proto/std.proto proto/fact.proto proto/max.proto proto/root.proto 
```