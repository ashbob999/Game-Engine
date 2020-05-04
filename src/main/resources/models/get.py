import requests

url = "https://github.com/lwjglgamedev/lwjglbook/blob/master/chapter09/src/main/resources/models/bunny.obj?raw=true"

r = requests.get(url = url)

print(r.text)
