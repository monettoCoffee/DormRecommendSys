# coding=utf-8
import flask
from handler import local_calculate
import os
import sys

app = flask.Flask(__name__)


@app.route("/")
def root_index():
    res = local_calculate.forecast(2)
    #todo 归一化!!!!!
    return "ASSAS"
    return res.replace("cluster id", "学生ID").replace("element", "问题ID")


if os.popen("lsof -i:7777").readlines():
    print("DRS: Calculate Module Boot Successful!")
    sys.stdout.flush()
if __name__ == "__main__":
    if sys.version_info.major != 3:
        print("DRS: Calculate Module Should Be Booted in Python3!")
    app.run(
        host='127.0.0.1',
        port=7777,
        debug=True
    )
