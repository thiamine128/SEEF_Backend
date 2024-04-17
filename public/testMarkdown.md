## 工业软件导论 第二次作业

> 21377206 阮阳栋

### 代码

```python
import numpy as np
from stl import mesh


def makeface():
    return np.array([ # 这是将长方体每个面转化为三角形
        [0, 3, 2], [0, 2, 1], [4, 5, 6], [4, 6, 7],
        [0, 4, 7], [0, 7, 3], [1, 2, 6], [1, 6, 5],
        [0, 1, 5], [0, 5, 4], [2, 3, 7], [2, 7, 6]
    ])


def pre(vertices):
    cube_mesh = mesh.Mesh(np.zeros(12, dtype=mesh.Mesh.dtype))
    faces = makeface()
    i = 0
    for f in faces:
        for j in range(3):
            cube_mesh.vectors[i][j] = vertices[f[j], :]
        i += 1
    return cube_mesh


def func(vertices, axis, degree):
    cube_mesh = pre(vertices)
    cube_mesh.rotate([0.5, 0.5, 0.5], np.radians(degree), axis)
    return cube_mesh


v_inp = []
for i in range(8):
    v_inp.append(list(map(int, input().split())))  # 输入长方体顶点
v = np.array(v_inp)
r = list(map(int, input().split()))  # 输入旋转轴
a = int(input())  # 输入旋转角度

p = pre(v)
p.save('pre.stl') # 保存旋转前的长方体
result = func(v, r, a)
result.save('result.stl')  # 旋转后的长方体保存为stl格式
```

函数解释：

* `makeface()`：返回一个列表，代表着长方体每个面（分成了两个三角形）
* `pre(vertices)`：返回旋转前的长方体
* `func(vertices, axis, degree)`：返回旋转后的长方体

### 输入样例

```python
-2 0 0
0 -2 0
2 0 0
0 2 0
-2 0 1
0 -2 1
2 0 1
0 2 1

1 2 1

22
```

上述前八行输入了一个长方体的顶点，之后输入了旋转轴`(1, 2, 1)`，最后输入旋转角度`22`度

公式测试：$\sum_{i=1}^n q_i + y = \frac{\sum_{i=1}^{n}a_i\sum_{i=1}^nb_i}{\Pi_{i=1}^n(a_i+b_i)}$

图片测试：

![]( http://localhost:5653/favicon.ico)
