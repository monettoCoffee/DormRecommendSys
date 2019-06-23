# coding=utf-8
import core.hierarchical_cluster as hierarchical_cluster
from weigh.cosine_similarity import CosineSimilarity as CosineSimilarity


class QuestionClusterVector(hierarchical_cluster.ClusterVector):
    def __init__(self, chosen_data, ratio):
        """
        :param chosen_meta_data: the chosen of question
        such as {"multi_chosen": {1: [0, 0, 1]} ...}
        :param ratio: every question's weight ratio
        """
        self.chosen_data = [chosen_data]
        self.weight_ratio = ratio

    def get_vector_distance(self, another_cluster_vector, calculate_method=CosineSimilarity):
        similarity = 0

        for self_chosen_data in self.chosen_data:
            self_one_chosen = self_chosen_data["one_chosen"]
            self_multi_chosen = self_chosen_data["multi_chosen"]

            for another_chosen_data in another_cluster_vector.chosen_data:
                distance = 0
                another_one_chosen = another_chosen_data["one_chosen"]
                for one_chosen in self_one_chosen:
                    if self_one_chosen[one_chosen] == another_one_chosen[one_chosen]:
                        distance += self.weight_ratio[one_chosen] * 1

                another_multi_chosen = another_chosen_data["multi_chosen"]
                for multi_chosen in self_multi_chosen:
                    distance += calculate_method.distance(self_multi_chosen[multi_chosen],
                                                          another_multi_chosen[multi_chosen]) \
                                * weight_ratio[multi_chosen]
                similarity = distance \
                    if hierarchical_cluster.compare_distance(calculate_method, distance > similarity) \
                    else similarity
            return similarity

    def merge(self, another_cluster_vector):
        self_chosen_data = self.chosen_data
        for another_chosen_data in another_cluster_vector.chosen_data:
            self_chosen_data.append(another_chosen_data)

    def __str__(self):
        msg = ""
        for chosen_data in self.chosen_data:
            msg += str(chosen_data)
        return msg


def init_cluster(compound_vector, ratio):
    """
    :param compound_vector: compound-multi-dimensional vector
    :param ratio: every question's ratio
    :return: the collection of vector that come from vector
    """
    cluster_list = []
    index = 0
    for cluster_vector in compound_vector:
        cluster_list.append(hierarchical_cluster.Cluster(QuestionClusterVector(cluster_vector, ratio), index))
        index += 1
    return cluster_list


if __name__ == "__main__":
    all_question_cluster_vector = [
        {
            'one_chosen': {1: 1},
            'multi_chosen':
                {
                    5: [1.0, 1.0],
                    6: [1.0, 1.0, 0.0, 0.0, 0.0, 1.0]
                }
        }, {
            'one_chosen': {1: 1},
            'multi_chosen':
                {
                    5: [1.0, 1.0],
                    6: [0.0, 0.0, 1.0, 0.0, 1.0, 1.0]
                }
        }, {
            'one_chosen': {1: 2},
            'multi_chosen':
                {
                    5: [1.0, 1.0],
                    6: [1.0, 1.0, 0.0, 0.0, 1.0, 0.0]
                }
        }, {
            'one_chosen': {1: 2},
            'multi_chosen':
                {
                    5: [1.0, 1.0],
                    6: [0.0, 0.0, 1.0, 1.0, 0.0, 1.0]
                }
        }
    ]

    weight_ratio = {
        1: 0.1,
        5: 1,
        6: 1,
    }
    cluster_number = 2

    cluster_list = init_cluster(all_question_cluster_vector, weight_ratio)
    hierarchical_cluster.analyse(cluster_list, cluster_number, CosineSimilarity)
    for ele in cluster_list:
        print(str(ele))
