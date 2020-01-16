# coding=utf-8
import math


class EuclideanDistance:
    BIGGER_BETTER = False

    @classmethod
    def distance(cls, vec1, vec2):
        between_distance = 0
        for ele1, ele2 in zip(vec1, vec2):
            between_distance += math.pow((ele1 - ele2), 2)
        result = math.sqrt(between_distance)
        return round(result, 5)


if __name__ == "__main__":
    vector1 = [1, 3, 7]
    vector2 = [2, 9, 0]
    msg = 'Euclidean Distance: \n  vector1: %s,\n  vector2: %s,\n  distance: %s' \
          % (str(vector1), str(vector2), str(EuclideanDistance.distance(vector1, vector2)))
    print(msg)
