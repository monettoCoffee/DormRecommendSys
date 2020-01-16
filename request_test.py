# coding=utf-8
import json
import requests

PARENT_URL = "http://127.0.0.1:"
CONFIG = [
    # Server Student Chosen
    {
        "url": "8080/chosen",
        "type": "post",
        "headers": {
            "content-type": "application/json",
        },
        "param": {
            "chosen": "561qw",
        }
    },
]


def request(config):
    config["type"] = config["type"].upper()
    if not config["url"].startswith("http"):
        config["url"] = PARENT_URL + config["url"]
    if config["type"] == "GET":
        config["result"] = requests.get(url=config["url"], params=config["param"], headers=config["param"])
    elif config["type"] == "POST":
        config["result"] = requests.post(url=config["url"], params=config["param"], headers=config["param"])
    config["result"] = json.loads(config["result"].text)


if __name__ == "__main__":
    for conf in CONFIG:
        request(conf)
        result = conf["result"]
        print("\n" + conf["type"] + ": " + conf["url"])
        for key in result:
            print(str(key) + ": " + str(result[key]))
