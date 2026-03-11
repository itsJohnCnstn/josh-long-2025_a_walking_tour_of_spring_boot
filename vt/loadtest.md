# tool to test

[hey!](https://github.com/rakyll/hey)

# test

```shell
hey -n 1000 -c 100 http://localhost:8083/delay
```

## w/o virtual threads

Summary:
Total:	200.0539 secs
Slowest:	16.9798 secs
Fastest:	5.1220 secs
Average:	9.3699 secs
Requests/sec:	4.9987

Total data:	11022 bytes
Size/request:	334 bytes

Response time histogram:
5.122 [1]	|■■■
6.308 [14]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
7.494 [3]	|■■■■■■■■■
8.679 [0]	|
9.865 [0]	|
11.051 [3]	|■■■■■■■■■
12.237 [5]	|■■■■■■■■■■■■■■
13.422 [0]	|
14.608 [0]	|
15.794 [0]	|
16.980 [7]	|■■■■■■■■■■■■■■■■■■■■


Latency distribution:
10%% in 5.1962 secs
25%% in 5.8575 secs
50%% in 6.5469 secs
75%% in 11.6683 secs
90%% in 16.8159 secs
95%% in 16.9798 secs
0%% in 0.0000 secs

Details (average, fastest, slowest):
DNS+dialup:	0.0031 secs, 0.0000 secs, 0.0059 secs
DNS-lookup:	0.0009 secs, 0.0000 secs, 0.0016 secs
req write:	0.0001 secs, 0.0000 secs, 0.0004 secs
resp wait:	9.3666 secs, 5.1218 secs, 16.9754 secs
resp read:	0.0002 secs, 0.0001 secs, 0.0005 secs

Status code distribution:
[200]	33 responses

Error distribution:
[967]	Get "http://localhost:8083/delay": context deadline exceeded (Client.Timeout exceeded while awaiting headers)

## with virtual threads

Summary:
Total:	55.7836 secs
Slowest:	7.0012 secs
Fastest:	0.2839 secs
Average:	5.3972 secs
Requests/sec:	17.9264

Total data:	333332 bytes
Size/request:	333 bytes

Response time histogram:
0.284 [1]	|
0.956 [1]	|
1.627 [0]	|
2.299 [0]	|
2.971 [0]	|
3.643 [0]	|
4.314 [0]	|
4.986 [0]	|
5.658 [799]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
6.330 [188]	|■■■■■■■■■
7.001 [11]	|■


Latency distribution:
10%% in 5.1207 secs
25%% in 5.1417 secs
50%% in 5.2898 secs
75%% in 5.5719 secs
90%% in 5.8640 secs
95%% in 6.0959 secs
99%% in 6.3684 secs

Details (average, fastest, slowest):
DNS+dialup:	0.0006 secs, 0.0000 secs, 0.0083 secs
DNS-lookup:	0.0002 secs, 0.0000 secs, 0.0021 secs
req write:	0.0000 secs, 0.0000 secs, 0.0023 secs
resp wait:	5.3963 secs, 0.2828 secs, 6.9945 secs
resp read:	0.0001 secs, 0.0000 secs, 0.0083 secs

Status code distribution:
[200]	998 responses
[500]	2 responses

# 

Summary:
Total:	55.7836 secs
Slowest:	7.0012 secs
Fastest:	0.2839 secs
Average:	5.3972 secs
Requests/sec:	17.9264

Total data:	333332 bytes
Size/request:	333 bytes

Response time histogram:
0.284 [1]	|
0.956 [1]	|
1.627 [0]	|
2.299 [0]	|
2.971 [0]	|
3.643 [0]	|
4.314 [0]	|
4.986 [0]	|
5.658 [799]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
6.330 [188]	|■■■■■■■■■
7.001 [11]	|■


Latency distribution:
10%% in 5.1207 secs
25%% in 5.1417 secs
50%% in 5.2898 secs
75%% in 5.5719 secs
90%% in 5.8640 secs
95%% in 6.0959 secs
99%% in 6.3684 secs

Details (average, fastest, slowest):
DNS+dialup:	0.0006 secs, 0.0000 secs, 0.0083 secs
DNS-lookup:	0.0002 secs, 0.0000 secs, 0.0021 secs
req write:	0.0000 secs, 0.0000 secs, 0.0023 secs
resp wait:	5.3963 secs, 0.2828 secs, 6.9945 secs
resp read:	0.0001 secs, 0.0000 secs, 0.0083 secs

Status code distribution:
[200]	998 responses
[500]	2 responses

# test

```shell
hey -n 40 -c 20 http://localhost:8083/delay
```
## w/o virtual threads

Summary:
Total:	22.9326 secs
Slowest:	16.4691 secs
Fastest:	5.1900 secs
Average:	9.3985 secs
Requests/sec:	1.7442

Total data:	13360 bytes
Size/request:	334 bytes

Response time histogram:
5.190 [1]	|■■■
6.318 [8]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■
7.446 [7]	|■■■■■■■■■■■■■■■■■■■■■■■
8.574 [0]	|
9.702 [0]	|
10.830 [12]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
11.957 [7]	|■■■■■■■■■■■■■■■■■■■■■■■
13.085 [2]	|■■■■■■■
14.213 [0]	|
15.341 [0]	|
16.469 [3]	|■■■■■■■■■■


Latency distribution:
10%% in 5.7372 secs
25%% in 6.4633 secs
50%% in 10.3568 secs
75%% in 11.5078 secs
90%% in 12.3639 secs
95%% in 16.2489 secs
0%% in 0.0000 secs

Details (average, fastest, slowest):
DNS+dialup:	0.0023 secs, 0.0000 secs, 0.0055 secs
DNS-lookup:	0.0007 secs, 0.0000 secs, 0.0016 secs
req write:	0.0003 secs, 0.0000 secs, 0.0006 secs
resp wait:	9.3954 secs, 5.1899 secs, 16.4623 secs
resp read:	0.0002 secs, 0.0000 secs, 0.0005 secs

Status code distribution:
[200]	40 responses

## with virtual threads

Summary:
Total:	12.2328 secs
Slowest:	6.5359 secs
Fastest:	5.1212 secs
Average:	5.6568 secs
Requests/sec:	3.2699

Total data:	13360 bytes
Size/request:	334 bytes

Response time histogram:
5.121 [1]	|■■■
5.263 [7]	|■■■■■■■■■■■■■■■■■■■■■■■
5.404 [5]	|■■■■■■■■■■■■■■■■■
5.546 [3]	|■■■■■■■■■■
5.687 [0]	|
5.829 [12]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
5.970 [4]	|■■■■■■■■■■■■■
6.112 [1]	|■■■
6.253 [4]	|■■■■■■■■■■■■■
6.394 [1]	|■■■
6.536 [2]	|■■■■■■■


Latency distribution:
10%% in 5.1271 secs
25%% in 5.2911 secs
50%% in 5.6940 secs
75%% in 5.9217 secs
90%% in 6.2461 secs
95%% in 6.4849 secs
0%% in 0.0000 secs

Details (average, fastest, slowest):
DNS+dialup:	0.0023 secs, 0.0000 secs, 0.0052 secs
DNS-lookup:	0.0008 secs, 0.0000 secs, 0.0018 secs
req write:	0.0002 secs, 0.0000 secs, 0.0006 secs
resp wait:	5.6540 secs, 5.1211 secs, 6.5294 secs
resp read:	0.0001 secs, 0.0000 secs, 0.0006 secs

Status code distribution:
[200]	40 responses