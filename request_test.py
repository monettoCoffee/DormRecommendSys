# coding=utf-8
import json
import requests

PARENT_URL = "http://127.0.0.1:"
CONFIG = [
    # Server Student Chosen
    # {
    #     "url": "8080/register/info",
    #     "type": "post",
    #     "headers": {
    #         "content-type": "application/json",
    #     },
    #     "param": {
    #         "uid": "0001",
    #     }
    # }, {
    #     "url": "8080/register/account",
    #     "type": "post",
    #     "headers": {
    #         "content-type": "application/json",
    #     },
    #     "param": {
    #         "uid": "0001",
    #         "account": "account001",
    #         "password": "password001",
    #         "phone": "11111",
    #         "mail": "mail@mail.com"
    #     }
    # },
    {
        "url": "8080/login",
        "type": "post",
        "headers": {
            "content-type": "application/json",
        },
        "param": {
            # "uid": "0001",
            # "account": "account001",
            # "password": "password001",
            # "phone": "11111",
            # "mail": "mail@mail.com",
            # "token": "123token"
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
