# coding=utf-8
from model.dorm_question import QuestionHandler
from model.dorm_user_chosen import UserChosenHandler
from model.dorm_question_plan import QuestionPlanHandler

from weigh.cosine_similarity import CosineSimilarity

import format.normalization as normalization
import core.hierarchical_cluster as hierarchical_cluster
import component.analyse_weigh_ratio as analyse_weight_ratio


# 从MySQL读取选择数据
def load_data(user_chosen_list, question_info):
    # question_option_dict record every option for create vector
    question_option_dict = {}
    # {k: qid, v: question's_option_set(options)}

    # user_id_list record user id through index
    user_id_list = []

    for user_chosen in user_chosen_list:
        user_id_list.append(user_chosen.uid)
        # Scan all user chosen, user chosen contains uid, cid, chosen(result)
        person_chosen_list = user_chosen.chosen
        # Scan user's chosen, it contains every chosen of question
        for chosen in person_chosen_list:
            # Scan user every chosen of question, such as question_id, question_result
            qid = chosen['qid']
            # todo 得到qid后要和问题类型做映射, 这里仅针对多选题
            # Create qid <-> user_chosen dict
            # Get specific option of question
            question_type = question_info[qid]["question_type"]
            if question_type == 2:
                structure_multi_option_question(qid, question_option_dict, chosen['result'])
            elif question_type == 3:
                structure_section_question()

    question_option_vector_index_dict = get_question_option_index_dict_by_option_dict(question_option_dict)
    return question_option_vector_index_dict


# 解析选择题类型为多选类型问题
def structure_multi_option_question(qid, question_option_dict, result):
    option_set = question_option_dict.get(qid, None)
    if not option_set:
        option_set = set()
        question_option_dict[qid] = option_set
    for option in result:
        option_set.add(option["word"])


# 解析选择题类型为区间选择类型问题
def structure_section_question():
    pass


# 通过问题选项set, 构建出index
def get_question_option_index_dict_by_option_dict(question_option_dict):
    # question_option_vector_index_dict record the option <-> vector index
    question_option_vector_index_dict = {}
    # {k: qid, v: dict(option <-> vector_index)}
    for qid in question_option_dict:
        question_mapping_dict = question_option_vector_index_dict.get(qid, None)
        if not question_mapping_dict:
            question_mapping_dict = {}
            question_option_vector_index_dict[qid] = question_mapping_dict
        option_index = 0
        for option in question_option_dict[qid]:
            question_mapping_dict[option] = option_index
            option_index += 1
    return question_option_vector_index_dict


# 构建答案的Cluster list
def structure_cluster_list(user_chosen_list, question_info, option_vector_index_dict):
    all_cluster_vector = []
    for user_chosen in user_chosen_list:
        person_chosen_list = user_chosen.chosen
        user_chosen_mark = {}
        for chosen in person_chosen_list:
            qid = chosen["qid"]
            question_type = question_info[qid]["question_type"]
            if question_type == 1:
                one_chosen_dict = user_chosen_mark.get("one_chosen", None)
                if not one_chosen_dict:
                    one_chosen_dict = {}
                    user_chosen_mark["one_chosen"] = one_chosen_dict
                one_chosen_dict[qid] = chosen["radio"]
            elif question_type == 2:
                multi_chosen_dict = user_chosen_mark.get("multi_chosen", None)
                if not multi_chosen_dict:
                    multi_chosen_dict = {}
                    user_chosen_mark["multi_chosen"] = multi_chosen_dict
                option_mapping = option_vector_index_dict[qid]
                user_multi_chosen_vector = [0] * len(option_mapping)
                for result in chosen["result"]:
                    user_multi_chosen_vector[option_mapping[result["word"]]] = result["stress"] + 1
                multi_chosen_dict[qid] = [user_multi_chosen_vector]
            elif question_type == 3:
                section_chosen_dict = user_chosen_mark.get("section_chosen", None)
                if not section_chosen_dict:
                    section_chosen_dict = {}
                    user_chosen_mark["section_chosen"] = section_chosen_dict
                section_chosen_result = chosen["result"]
                for section_chosen in section_chosen_result:
                    section_word = section_chosen["word"].split("-")
                    section_chosen_dict[qid] = {
                        "stress": section_chosen["stress"] + 1,
                        "section": [section_word[0].strip(), section_word[1].strip()]
                    }
        all_cluster_vector.append(user_chosen_mark)
    return all_cluster_vector


def data_format(all_cluster_vector):
    for every_person_chosen in all_cluster_vector:
        multi_chosen = every_person_chosen["multi_chosen"]
        for chosen_qid in multi_chosen:
            normalization.vector_normalization(multi_chosen[chosen_qid][0])


def forecast(cluster_number):
    # 通过计划id获取所有需要进行分析的问题
    plan_qid_list = QuestionPlanHandler.get_question_plan_by_pid(1).qid
    # 通过问题id获取分析问题所需要的信息
    question_info = QuestionHandler.get_question_info_by_qid(plan_qid_list, trans_dict=True)
    # 通过plan id获取本次计划参与的学生
    user_chosen_list = UserChosenHandler.get_all_user_chosen_by_pid(1)

    # 构建多选题的option与index关系
    question_option_vector_index_dict = load_data(user_chosen_list, question_info)
    # 通过选择构建问题cluster原型
    all_cluster_vector = structure_cluster_list(user_chosen_list, question_info,
                                                question_option_vector_index_dict)

    # 进行数据标准化处理 todo 需要修改
    data_format(all_cluster_vector)
    print("all_cluster_vector: " + str(all_cluster_vector))
    # 自动分析问题的权重 Todo 需要修改
    weight_ratio = analyse_weight_ratio.direct_analyse(all_cluster_vector, question_info)
    print("weight_ratio: " + str(weight_ratio))
    # 构建cluster list
    return
    cluster_list = compound_cluster.init_cluster(all_cluster_vector, weight_ratio)
    # 层次聚类
    hierarchical_cluster.analyse(cluster_list, cluster_number, calculate_method=CosineSimilarity)
    msg = ""
    for cluster in cluster_list:
        print(str(cluster))
        msg += str(cluster) + "\n<br>"
    return msg


def show_table_of_chosen():
    pass
