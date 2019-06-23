# coding=utf-8
import os
import time
import signal
import platform
import threading
import subprocess

# TODO 判断成功启动的条件是端口是否被成功监听！！！！！

ROOT_PATH = os.getcwd()
BOOT_CONFIG_LIST = [
    {
        "application_dir": ROOT_PATH + "/DRS-Calculate",
        "pre_deploy_cmd": "",
        "boot_cmd": "python3 -u app.py",
        "complete_mark": "DRS: Calculate Module Boot Successful!",
        # todo 加入 Fasle Mark
        "false_mark": ["Be Booted in Python3!"],
        "host": "monetto@127.0.0.1",
        "port": "7777",
        "console_log": True,
        "boot": False,
    },
    {
        "application_dir": ROOT_PATH + "/DRS-Server",
        "pre_deploy_cmd": "",
        "boot_cmd": "mvn spring-boot:run",
        "complete_mark": "DRS: Server Module Boot Successful!",
        "host": "monetto@127.0.0.1",
        "port": "8080",
        "console_log": True,
        "boot": True,
    },
]
PORT_LIST = []


def thread_function(config, boot_mark_list, mark_index):
    if not config["boot"]:
        boot_mark_list[mark_index] = True
        return
    while mark_index != 0 and not boot_mark_list[mark_index - 1]:
        continue
    os.chdir(config["application_dir"])
    os.popen(config["pre_deploy_cmd"])
    application_process = subprocess.Popen(config["boot_cmd"], shell=True, stdout=subprocess.PIPE)
    for boot_info in iter(application_process.stdout.readline, 'b'):
        if boot_info:
            if config["console_log"]:
                print(boot_info.decode("utf8").replace("\n", ''))
            if config["complete_mark"] in str(boot_info):
                boot_mark_list[mark_index] = True
                if not config["console_log"]:
                    print(config["complete_mark"])


def signal_handler(signal, frame):
    print("DRS: Cancel Deploy!")
    os._exit(0)


signal.signal(signal.SIGINT, signal_handler)


def cancel_deploy():
    current_system = platform.system()
    if "Darwin" in current_system or "Linux" in current_system:
        for port in PORT_LIST:
            stop_application_nix(port)
    elif "Windows" in current_system:
        stop_application_win()
    else:
        print("DRS: Unknown System, Can't Stop Application Before Deploy")


def stop_application_win():
    pass


def stop_application_nix(port):
    result = os.popen("lsof -i:" + port).readlines()
    index = len(result) - 1
    while index > 0:
        line = result[index]
        info_list = line.split(' ')
        info_index = 1
        while True:
            if info_list[info_index] != '':
                pid = info_list[info_index]
                os.popen("kill -9 " + str(pid))
                break
            else:
                info_index += 1
        index -= 1


if __name__ == "__main__":
    boot_mark = []
    boot_index = 0
    thread_list = []
    for boot_config in BOOT_CONFIG_LIST:
        boot_mark.append(False)
        thread_list.append(threading.Thread(target=thread_function,
                                            args=(BOOT_CONFIG_LIST[boot_index], boot_mark, boot_index,)))
        PORT_LIST.append(boot_config["port"])
        boot_index += 1
    cancel_deploy()
    print("DRS: Start Deploy Application!")
    for thread in thread_list:
        thread.start()
    for thread in thread_list:
        while thread.isAlive():
            time.sleep(1000)
