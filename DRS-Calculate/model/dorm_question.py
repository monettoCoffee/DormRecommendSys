# coding=utf-8
from config.database_session import *


class Question(OrmBase, Base):
    __table__ = Table(
        'dorm_question', metadata,
        Column('qid', Integer, primary_key=True, autoincrement=True, doc='问题ID'),
        Column('did', Integer, doc='问题方向大类别'),
        Column('tips', String, doc='问题前端左侧提示'),
        Column('introduction', String, doc='问题标题'),
        Column('display_type', SmallInteger, doc='问题显示类型'),
        Column('choose_type', SmallInteger, doc='问题选择数量'),
        Column('question_type', SmallInteger, doc='问题选择方式'),
        Column('option_json', String, doc='问题选择项的JSON序列化'),
        Column('auto_weight', SmallInteger, doc='是否使用自动权重(默认是)'),
        Column('weight', SmallInteger, doc='手动指定权重大小'),
    )


class QuestionHandler(object):
    @classmethod
    def get_question_info_by_qid(cls, qid, trans_dict=False):
        with get_session() as session:
            question = session.query(
                Question.qid,
                Question.did,
                Question.display_type,
                Question.choose_type,
                Question.question_type,
                Question.auto_weight,
                Question.weight
            )
            if type(qid) == list:
                question = question.filter(Question.qid.in_(qid)).all()
            else:
                question = question.filter(Question.qid == qid).all()
            if not trans_dict:
                return question
            else:
                result = {}
                for element in question:
                    result[element.qid] = {
                        "qid": element.qid,
                        "did": element.did,
                        "display_type": element.display_type,
                        "choose_type": element.choose_type,
                        "question_type": element.question_type,
                        "auto_weight": element.auto_weight,
                        "weight": element.weight
                    }
                return result
