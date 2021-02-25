curl -X POST 'localhost:8000/users?id=1&name=admin&currency=euro'
curl -X POST 'localhost:8000/users?id=2&name=guest&currency=rouble'
curl -X GET 'localhost:8000/users'
curl -X POST 'localhost:8000/products?id=1&name=TV&price=100.0'
curl -X GET 'localhost:8000/products?id=1'
curl -X GET 'localhost:8000/products?id=2'

