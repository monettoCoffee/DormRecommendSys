# coding=utf-8
from weigh.cosine_similarity import CosineSimilarity

import component.section_calculator as section_calculator


# 分析向量平均相似度
def direct_analyse(all_cluster_vector, question_info):
    weight_ratio = {}

    analyse_one_chosen_weight_ratio(all_cluster_vector, question_info, weight_ratio)
    analyse_multi_chosen_weight_ratio(all_cluster_vector, question_info, weight_ratio)
    analyse_section_chosen_weight_ratio(all_cluster_vector, question_info, weight_ratio)

    for qid in weight_ratio:
        weight_ratio[qid] = 1 - weight_ratio[qid]
    return weight_ratio


def get_auto_analyse_question_qid(example_all_cluster_vector_element, question_info, chosen_key):
    chosen_qid_list = []
    for person_one_chosen_qid in example_all_cluster_vector_element[chosen_key]:
        if question_info[person_one_chosen_qid]["auto_weight"] == 1:
            chosen_qid_list.append(person_one_chosen_qid)
    return chosen_qid_list


def analyse_one_chosen_weight_ratio(all_cluster_vector, question_info, weight_ratio):
    one_chosen_qid_list = get_auto_analyse_question_qid(all_cluster_vector[0], question_info, "one_chosen")
    for one_chosen_question_qid in one_chosen_qid_list:
        one_chosen_qid_radio_max_number = -1
        one_chosen_question_radio_dict = weight_ratio.get(one_chosen_question_qid, None)
        if not one_chosen_question_radio_dict:
            one_chosen_question_radio_dict = {}
            weight_ratio[one_chosen_question_qid] = one_chosen_question_radio_dict
        for cluster_vector in all_cluster_vector:
            radio_index = cluster_vector["one_chosen"][one_chosen_question_qid]
            radio_choice_times = one_chosen_question_radio_dict.get(radio_index, None)
            if not radio_choice_times:
                radio_choice_times = 0
            radio_choice_times += 1
            one_chosen_question_radio_dict[radio_index] = radio_choice_times
            one_chosen_qid_radio_max_number = max(one_chosen_qid_radio_max_number, radio_choice_times)
        weight_ratio[one_chosen_question_qid] = one_chosen_qid_radio_max_number / len(all_cluster_vector)


def analyse_multi_chosen_weight_ratio(all_cluster_vector, question_info, weight_ratio):
    multi_chosen_qid_list = get_auto_analyse_question_qid(all_cluster_vector[0], question_info, "multi_chosen")
    for multi_chosen_question_qid in multi_chosen_qid_list:
        for cluster_vector in all_cluster_vector:
            chosen_list = weight_ratio.get(multi_chosen_question_qid, None)
            if not chosen_list:
                chosen_list = []
                weight_ratio[multi_chosen_question_qid] = chosen_list
            chosen_list.append(cluster_vector["multi_chosen"][multi_chosen_question_qid])

    for multi_chosen_question_qid in multi_chosen_qid_list:
        distance = 0
        calculate_count = 0
        all_qid_chosen_vector = weight_ratio[multi_chosen_question_qid]
        chosen_outside_index = len(all_qid_chosen_vector) - 1
        while chosen_outside_index > 0:
            chosen_inside_index = chosen_outside_index - 1
            while chosen_inside_index > -1:
                calculate_count += 1
                distance += CosineSimilarity.distance(all_qid_chosen_vector[chosen_inside_index][0],
                                                      all_qid_chosen_vector[chosen_outside_index][0])
                chosen_inside_index -= 1
            chosen_outside_index -= 1
        weight_ratio[multi_chosen_question_qid] = distance / calculate_count


def analyse_section_chosen_weight_ratio(all_cluster_vector, question_info, weight_ratio):
    section_chosen_qid_list = get_auto_analyse_question_qid(all_cluster_vector[0], question_info, "section_chosen")
    cluster_outside_index = len(all_cluster_vector) - 1
    while cluster_outside_index > 0:
        cluster_outside_section_chosen = all_cluster_vector[cluster_outside_index]["section_chosen"]
        cluster_inside_index = cluster_outside_index - 1
        while cluster_inside_index > -1:
            cluster_inside_section_chosen = all_cluster_vector[cluster_inside_index]["section_chosen"]
            for section_chosen_question_qid in section_chosen_qid_list:
                section_calculator.time_section_coincide_calculate(
                    cluster_outside_section_chosen[section_chosen_question_qid],
                    cluster_inside_section_chosen[section_chosen_question_qid])
            cluster_inside_index -= 1
        cluster_outside_index -= 1
