# coding=utf-8
import math


class PearsonCoefficient:
    BIGGER_BETTER = True

    @classmethod
    def distance(cls, vec1, vec2):
        average_vec1 = get_vector_average(vec1)
        average_vec2 = get_vector_average(vec2)

        numerator = 0
        for ele1, ele2 in zip(vec1, vec2):
            numerator += (ele1 - average_vec1) * (ele2 - average_vec2)

        denominator = get_vector_expect(vec1, average_vec1) * get_vector_expect(vec2, average_vec2)
        return numerator / float(denominator)


def get_vector_average(vector):
    average_number = 0
    for element in vector:
        average_number += element
    return average_number / float(len(vector))


def get_vector_expect(vector, average):
    expect = 0
    for element in vector:
        expect += math.pow(element - average, 2)
    return math.sqrt(expect)


if __name__ == "__main__":
    vector1 = [1, 2, 3, 5, 8]
    vector2 = [0.11, 0.12, 0.13, 0.15, 0.18]

    v1 = [9, 1, 4, 2, 5]
    v2 = [2.9, 2.1, 2.4, 2.2, 2.5]
    msg = 'Pearson Coefficient: \n  vector1: %s,\n  vector2: %s,\n  distance: %s' \
          % (str(v1), str(vector2), str(PearsonCoefficient.distance(v1, vector2)))
    print(msg)
