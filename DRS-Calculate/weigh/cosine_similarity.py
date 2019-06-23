# coding=utf-8
import math


class CosineSimilarity:
    BIGGER_BETTER = True

    @classmethod
    def distance(cls, vec1, vec2):
        numerator = 0
        denominator_vec1, denominator_vec2 = 0, 0
        for ele1, ele2 in zip(vec1, vec2):
            numerator += ele1 * ele2
            denominator_vec1 += math.pow(ele1, 2)
            denominator_vec2 += math.pow(ele2, 2)
        return numerator / (math.sqrt(denominator_vec1) * math.sqrt(denominator_vec2))


if __name__ == "__main__":
    vector1 = [4, 0, 0, 5, 1, 0, 0]
    vector2 = [5, 5, 4, 0, 0, 0, 0]
    msg = 'Cosine Similarity: \n  vector1: %s,\n  vector2: %s,\n  distance: %s' \
          % (str(vector1), str(vector2), str(CosineSimilarity.distance(vector1, vector2)))
    print(msg)
