# coding=utf-8
import sys
from weigh.cosine_similarity import CosineSimilarity
from weigh.euclidean_distance import EuclideanDistance
from weigh.pearson_coefficient import PearsonCoefficient


def compare_distance(calculate_method, bigger):
    return calculate_method.BIGGER_BETTER == bigger


# 获取对比数值的边界, 极大或极小
def get_edge(calculate_method):
    if calculate_method.BIGGER_BETTER:
        return -sys.maxsize
    else:
        return sys.maxsize


class Cluster(object):
    def __init__(self, vector_list, cluster_id=None):
        """
        :param vector_list: multi-dimensional vector collection
        such as [ [1,2,3], [4,5,6] ]
        """
        if issubclass(type(vector_list), ClusterVector):
            self.vector_list = vector_list
        else:
            self.vector_list = ClusterVector(vector_list)
        if cluster_id is not None:
            cluster_id = [cluster_id]
        self.cluster_id = cluster_id

    def __str__(self):
        if self.cluster_id is not None:
            msg = "cluster id: %s, element:" % self.cluster_id
        else:
            msg = "cluster element: "
        msg += str(self.vector_list)
        return msg

    def __len__(self):
        return len(self.vector_list)

    # 计算两个Cluster之间的相似度/距离
    def get_distance(self, another_cluster, calculate_method=EuclideanDistance):
        return self.vector_list.get_vector_distance(another_cluster.vector_list, calculate_method)

    # 合并两个cluster
    def merge_cluster(self, another_cluster):
        if another_cluster.cluster_id is not None:
            for cluster_id in another_cluster.cluster_id:
                self.cluster_id.append(cluster_id)
        self.vector_list.merge(another_cluster.vector_list)


class ClusterVector(object):
    def __init__(self, cluster_vector):
        if type(cluster_vector[0]) != list:
            raise Exception('Cluster must be n*n vector, try [vector] ?')
        self.vector_list = cluster_vector

    def get_vector_distance(self, another_cluster_vector, calculate_method=EuclideanDistance):
        res = get_edge(calculate_method)
        for self_vector_element in self.vector_list:
            for another_cluster_vector_element in another_cluster_vector.vector_list:
                distance = calculate_method.distance(self_vector_element, another_cluster_vector_element)
                res = distance if compare_distance(calculate_method, distance > res) else res
        return res

    def merge(self, another_cluster_vector):
        self.vector_list += another_cluster_vector.vector_list

    def __str__(self):
        msg = ""
        for self_vector in self.vector_list:
            msg += " %s ," % str(self_vector)
        return msg[0: len(msg) - 1]

    def __len__(self):
        return len(self.vector_list)


def init_cluster(matrix):
    """
    :param matrix: multi-dimensional vector matrix
    :return: the collection of vector that come from matrix
    """
    cluster_list = []
    index = 0
    for cluster_vector in matrix:
        cluster_list.append(Cluster([cluster_vector], index))
        index += 1
    return cluster_list


def analyse(cluster_list, stop_number, cluster_elements_number=None, calculate_method=EuclideanDistance):
    """
    :param cluster_list: all of the cluster in this list
    :param stop_number: How many type of cluster
    :param calculate_method: A method to calculate the distance of two cluster
    """
    # 被合并的Cluster 与 合并到这个Cluster
    to_merge_cluster, be_merged_cluster = None, None
    # 记录被合并的Cluster的下标
    pop_index = -1
    if cluster_elements_number and len(cluster_list)/stop_number > cluster_elements_number:
        raise Exception("Stop_number * cluster_elements_number is smaller than total number")
    while len(cluster_list) > stop_number:
        distance = get_edge(calculate_method)
        in_index = len(cluster_list) - 1
        while in_index > 0:
            in_cluster = cluster_list[in_index]
            if cluster_elements_number and len(in_cluster) >= cluster_elements_number:
                in_index -= 1
                continue
            out_index = in_index - 1
            while out_index > -1:
                out_cluster = cluster_list[out_index]
                if cluster_elements_number and len(out_cluster) >= cluster_elements_number:
                    out_index -= 1
                    continue
                similarity = in_cluster.get_distance(out_cluster, calculate_method)
                if compare_distance(calculate_method, similarity > distance):
                    distance = similarity
                    be_merged_cluster = out_cluster
                    to_merge_cluster = in_cluster
                    pop_index = out_index
                out_index -= 1
            in_index -= 1
        # 合并Cluster
        to_merge_cluster.merge_cluster(be_merged_cluster)
        cluster_list.pop(pop_index)


def execute(vector_list, stop_number, cluster_elements_number=None, calculate_method=EuclideanDistance):
    """
    :param vector_list: multi-dimensional vector collection matrix(n x n)
    :param stop_number: How many type of cluster
    :param calculate_method: A method to calculate the distance of two cluster
    :return: the list of cluster that size is {stop_number}
    """
    all_cluster = init_cluster(vector_list)
    analyse(all_cluster, stop_number, cluster_elements_number, calculate_method)
    return all_cluster


if __name__ == "__main__":
    all_vector = [
        [1.1, 1.0, 1.4],

        [11.4, 11.2, 10.3],
        [9.7, 9.4, 9.8],
        [9.7, 10.4, 9.8],
        [9.7, 11.4, 9.8],
        [9.7, 12.4, 9.8],

        [98.4, 99.7, 95.9],
        [95.4, 97.2, 100.9],
        [95.4, 97.2, 97.9],
    ]
    cluster_number = 3
    cluster_elements_number = 3

    # result = execute(all_vector, cluster_number, calculate_method=EuclideanDistance)
    result = execute(all_vector, cluster_number, cluster_elements_number, EuclideanDistance)
    for vector in result:
        print(str(vector))
