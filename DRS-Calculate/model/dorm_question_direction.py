# coding=utf-8
from config.database_session import *


class QuestionDirection(OrmBase, Base):
    __table__ = Table(
        'dorm_question_direction', metadata,
        Column('did', Integer, primary_key=True, autoincrement=True, doc='问题方向ID'),
        Column('direction_name', String, doc='方向名'),
        Column('description', String, doc='方向描述'),
        Column('weight', Float, doc='问题方向权重'),
    )


class QuestionPlanHandler(object):
    @classmethod
    def get_all_dorm_direction(cls):
        with get_session() as session:
            question_direction = session.query(
                QuestionDirection.did,
                QuestionDirection.direction_name,
                QuestionDirection.description,
                QuestionDirection.weight,
            )
            question_direction = question_direction.all()
            return question_direction
