# coding=utf-8
from config.database_session import *


class UserChosen(OrmBase, Base):
    __table__ = Table(
        'dorm_user_chosen', metadata,
        Column('cid', Integer, primary_key=True, autoincrement=True, doc='选择结果ID'),
        Column('uid', Integer, default=None, doc='用户ID'),
        Column('pid', Integer, default=None, doc='参与选择计划ID'),
        Column('chosen_json', JSON, doc='用户选择结果的JSON数据'),
    )


class UserChosenHandler(object):
    @classmethod
    def get_user_chosen_by_cid(cls, uid):
        with get_session() as session:
            user_chosen = session.query(
                UserChosen.uid,
                UserChosen.cid,
                UserChosen.chosen_json.label("chosen")
            )
            if type(uid) == list:
                user_chosen = user_chosen.filter(UserChosen.uid.in_(uid)).all()
            else:
                user_chosen = user_chosen.filter(UserChosen.uid == uid).one()
            return user_chosen

    @classmethod
    def get_all_user_chosen_by_pid(cls, pid):
        with get_session() as session:
            user_chosen = session.query(
                UserChosen.uid,
                UserChosen.cid,
                UserChosen.pid,
                UserChosen.chosen_json.label("chosen")
            )
            if type(pid) != list:
                pid = [pid]
            user_chosen = user_chosen.filter(UserChosen.pid.in_(pid)).all()
            return user_chosen

    @classmethod
    def get_all_user_chosen(cls):
        with get_session() as session:
            user_chosen = session.query(
                UserChosen.uid,
                UserChosen.cid,
                UserChosen.pid,
                UserChosen.chosen_json.label("chosen")
            )
            return user_chosen.all()
