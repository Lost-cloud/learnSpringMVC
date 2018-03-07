redis.call('hset', 'red_packet_1', 'stock', 20000)
redis.call('hset', 'red_packet_1', 'unit_amount', 10)
redis.call('ltrim', 'red_packet_list_1', -1, 0)
