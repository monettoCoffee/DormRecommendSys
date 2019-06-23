# coding=utf-8
from config.database_session import *


class QuestionPlan(OrmBase, Base):
    __table__ = Table(
        'dorm_question_plan', metadata,
        Column('pid', Integer, primary_key=True, autoincrement=True, doc='选择计划ID'),
        Column('done', SmallInteger, doc='是否完成选择计划 0:未开始 1:完成计算'),
        Column('plan_name', String, doc='计划名称'),
        Column('description', String, doc='计划描述'),
        Column('qid_json', JSON, doc='计划包括哪些问题JSON序列化'),
    )


class QuestionPlanHandler(object):
    @classmethod
    def get_question_plan_by_pid(cls, pid):
        with get_session() as session:
            question_plan = session.query(
                QuestionPlan.pid,
                QuestionPlan.done,
                QuestionPlan.plan_name,
                QuestionPlan.description,
                QuestionPlan.qid_json.label("qid"),
            )
            if type(pid) == list:
                question_plan = question_plan.filter(QuestionPlan.pid.in_(pid)).all()
            else:
                question_plan = question_plan.filter(QuestionPlan.pid == pid).one()
            return question_plan

    @classmethod
    def get_question_plan(cls, get_all=True):
        with get_session() as session:
            question_plan = session.query(
                QuestionPlan.pid,
                QuestionPlan.done,
                QuestionPlan.plan_name,
                QuestionPlan.description,
                QuestionPlan.qid.label("qid"),
            )
            if get_all:
                question_plan = question_plan.all()
            else:
                question_plan = question_plan.filter(QuestionPlan.done == 0).all()
            return question_plan
