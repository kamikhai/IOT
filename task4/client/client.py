from __future__ import print_function

import logging

import grpc

import fact_pb2
import fact_pb2_grpc
import max_pb2
import max_pb2_grpc
import root_pb2
import root_pb2_grpc
import std_pb2_grpc
import std_pb2

server = 'localhost:5051'


def run_fact():
  num = 1234
  with grpc.insecure_channel(server) as channel:
    stub = fact_pb2_grpc.FactCalculatorStub(channel)
    responses = stub.calculate_fact(fact_pb2.Number(value=num))
    print(f"fact {1234}:")
    for response in responses:
      print(f"{response.value}")


def run_max():
  nums = [max_pb2.Number(value=1),
          max_pb2.Number(value=3),
          max_pb2.Number(value=5),
          max_pb2.Number(value=1),
          max_pb2.Number(value=2),
          max_pb2.Number(value=4),
          max_pb2.Number(value=1)]
  with grpc.insecure_channel(server) as channel:
    stub = max_pb2_grpc.MaxCalculatorStub(channel)
    responses = stub.calculate_max(iter(nums))
    print("max : ")
    for response in responses:
      print(f"{response.value}")


def run_root():
  num = 100
  with grpc.insecure_channel(server) as channel:
    stub = root_pb2_grpc.RootCalculatorStub(channel)
    response = stub.calculate_root(root_pb2.Number(value=num))
    print(f"root for {num} : " + str(response.value))


def run_std():
  nums = [std_pb2.Number(value=1),
          std_pb2.Number(value=3),
          std_pb2.Number(value=5),
          std_pb2.Number(value=1),
          std_pb2.Number(value=2),
          std_pb2.Number(value=4),
          std_pb2.Number(value=1)]
  with grpc.insecure_channel(server) as channel:
    stub = std_pb2_grpc.StdCalculatorStub(channel)
    response = stub.calculate_std(iter(nums))
    print(f"std: {response.value}")


if __name__ == '__main__':
  logging.basicConfig()
  run_root()
  run_std()
  run_fact()
  run_max()
