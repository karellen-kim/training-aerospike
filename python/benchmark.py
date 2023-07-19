import time
from typing import List

import aerospike
import random
import os
from dotenv import load_dotenv

load_dotenv()
host = os.getenv("HOST")
namespace = os.getenv("NAMESPACE")

class AerospikeClient :
    def __init__(self):
        self.client = aerospike.client({'hosts': [(host, 3000)]}).connect()
        self.key = (namespace, "test", 1)
        self.key_size = 6000
        self.bin_ids = [str(i) for i in range(1,10)]

    def write(self):
        bins = {str(num): str(num) for num in self.bin_ids}
        self.client.put(self.key, bins)

    def read(self, num: int):
        random.shuffle(client.bin_ids)
        ids = self.bin_ids[:num]

        (key, metadata, record) = self.client.select(self.key, ids)
        print(record)
        return record

    def close(self):
        self.client.close()

client = AerospikeClient()

def write():
    client.write()
    return True

def read(num: int):
    record = client.read(num)
    return record != None

def test_write(benchmark):
    result = benchmark(write)
    assert result == True

def test_10read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 10 }, iterations=50, rounds=100)
    assert result != None

def test_100read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 100 }, iterations=50, rounds=100)
    assert result != None

def test_200read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 200 }, iterations=50, rounds=100)
    assert result != None

def test_400read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 400 }, iterations=50, rounds=100)
    assert result != None

def test_600read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 600 }, iterations=50, rounds=100)
    assert result != None

def test_800read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 800 }, iterations=50, rounds=100)
    assert result != None

def test_1000read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 1000 }, iterations=50, rounds=500)
    assert result != None

def test_2000read(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 2000 }, iterations=50, rounds=100)
    assert result != None

def test_halfread(benchmark):
    result = benchmark.pedantic(read, kwargs={ 'num': 3000 }, iterations=50, rounds=100)
    assert result != None