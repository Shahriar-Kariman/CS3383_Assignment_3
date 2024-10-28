# CS3383 - Assignment 3

**Author:** Shahriar Kariman

*Due:* $Oct\ 28_{th}, 2024$

## Question 1 - Significant Inversions

Significant Inversion of $i_{th}$ and $j_{th}$ elements:

$$
\begin{split}
  sequence \rightarrow a_1, a_2, a_3, ...\ , a_n
  \\
  i < j \ such \ that \ a_i > 2 \times a_j
\end{split}
$$

### A - Finding Number of Significant Inversions

My algorithm:

```py
def numSignificantInversions(a):
  # input: and array of numbers
  if len(a)<2:
    return (a,0)
  else:
    mid = len(a)/2
    right, num_1 = numSignificantInversions(a[:mid])
    left, num_2 = numSignificantInversions(a[mid:])
    result, num_3 = merge(right,left)
  return num_1+num_2+num_3, result

def merge(right, left):
  num = 0
  i,j = 0
  while(i<len(right) and j<len(left)):
    if right[i]>2*left[j]:
      num = len(left)-j
      j = j+1
    else:
      i = i+1
  i,j = 0
  result = []
  while(i<len(right) and j<len(left)):
    if right[i]<left[j]:
      result.add(right[i])
      i = i+1
    else:
      result.add(left[j])
      j = j+1
  if j<len(left):
    result.append(left[j:])
  elif i<len(right):
    result.append(right[i:])
  return num, result
```

#### Run Time Analysis using Master Theorem

Well the algorithm divides each input into 2 part that is $\Theta(1)$ and merges each section. Since during merging both *counting inversions* and *putting both arrays together* take looping through every elements of each array then merging is $\Theta(n)$.

That makes the recurrence equation:

$$
\begin{split}
  T(n) =
  \begin{cases}
    b \qquad if\ n<2
    \\
    2T(\frac{n}{2}) + bn \qquad if\ n \geq 2
  \end{cases}
\end{split}
$$

And that means:

$$
\begin{split}
  a=2,\ b=2\ and\ f(n) = \Theta(n)
  \\
  n^{\log_{b}{a}} = n
\end{split}
$$

Acorrding to case 2 of the master theorem since:

$$
\begin{split}
  f(n) = \Theta(n \times \log^{k}{n}) = \Theta(n^{\log_{b}{a}} \times \log^{k}{n})\ for\ k=0
\end{split}
$$

The algorithm is $\Theta(n^{\log_{b}{a}} \times \log^{k+1}{n}) = \Theta(n \log(n))$.

## Question 2 - Still Point Algorithm

My algorithm returns $-1$ for the when there are no indexes that $a_i = i$ cause what if $a_0 = 0$.

```py
# Unfortunatly I can't do operations with just one Array as a paremeter
# because doing A[:mid] or A[mid:] changes the indexes
def stillpoint(A, low, high):
  if low > high:
    return -1
  mid = (low + high)//2
  if A[mid] == mid:
    return mid
  if A[mid] > mid:
    return stillpoint(A, low, mid-1)
  return stillpoint(A, mid+1, high)
```

### Run Time Analysis

Lets say say $(high-low) = n$ in that case here is the recurence equation:

$$
\begin{split}
  T(n) =
  \begin{cases}
    \Theta(1) \qquad if\ n \leq 0
    \\
    T(\frac{n}{2}) + 0 \qquad if\ n > 0
  \end{cases}
\end{split}
$$

**Note:** $f(n)$ is $\Theta(n)$ because there is no merging step in this algorithm a lot like the binary search of a sorted array.

## Question 3 - Finding Peak Element

So the question is asking for only one peak I could just look at a random element compare it with the elements before and after and then decide what direction to go.

```py
def findPeak(arr, low, high):
  mid = (low + high)//2
  if (mid == 0 or arr[mid-1] <= arr[mid]) and (mid == (len(arr)-1) or arr[mid]>=arr[mid+1]):
    return mid
  elif mid > 0 and arr[mid-1] > arr[mid]:
    return findPeak(arr, low, mid)
  else:
    return findPeak(arr, mid, hight)
```

## Question 4 - Closest Sum

```py
def closestSum(A, x):
  index_a = 0
  index_b =  len(A) - 1
  closest_pair = { 'a': index_a, 'b':index_b }
  while index_a < index_b:
    curr_sum = A[index_a] + A[index_b]
    curr_dif = abs(x - curr_sum)
    if curr_sum < x:
      index_a = index_a + 1
    else:
      index_b = index_b - 1
    new_sum = A[index_a] + A[index_b]
    new_dif = abs(x - new_sum)
    if new_dif < curr_dif:
      closest_pair = { 'a': index_a, 'b': index_b }
  return closest_pair
```

Since we check each index only once by using variables `index_a` and `index_b` until the variables are equal then the run time analysis of the algorithm is $\Theta(n)$.

## Question 5 - Median Value of an Unsorted Array

I am going to be honest I struggled with this question for a bit but I finally figured out that maybe expecteed running time is the same as average running time and that made me think about quick sort which is not fair cause we didn't even go over quicksort in class and I had completely forgotten about it up to this point as a matter of fact I distinctly recall the professor mentioning that he was not going to give us questions about quicksort.

```py
def modifiedQuickSort(A, start, end):
  pivot = A[end]
  i = start - 1
  j = start
  while j < end:
    if A[j] < pivot:
      i += 1
      A[i], A[j] = A[j], A[i]
    j += j
  i += 1
  A[i], A[end] = A[end], A[i]
  if i == len(A)/2:
    return None
  if i < len(A)/2:
    modifiedQuickSort(A, i+1, end)
  else:
    modifiedQuickSort(A, start, i-1)

def findMedian(array):
  modifiedQuicSort(array, 0, len(array)-1)
  # After partially sorting the array
  # to the point where the element in
  # the center is the medain we can simply
  return A[len(A)/2]
```

I can use master theorem to calculate the avrage time complexity of the algorithm. Since on avrage each recursive call cuts the problem in half I could say the recurrence equation of this algorithm is:

$$
\begin{split}
  T(n) =
  \begin{cases}
    \Theta(1) \qquad if\ n<2
    \\
    T(\frac{n}{2}) + \Theta(n) \qquad if\ n \geq 2
  \end{cases}
\end{split}
$$

And that makes:

$$
\begin{split}
  a=1,\ b=2\ and\ f(n) = \Theta(n)
  \\
  n^{\log_{b}{a}} = n^0 = 1
\end{split}
$$

Acorrding to the case 3 of the master theorem since:

$$
\begin{split}
  f(n) = \Omega(n^{\log_{b}{a}+\epsilon}) \space for\ any\ \epsilon > 0
  \\
  and
  \\
  a f(\frac{n}{b}) = a \times \frac{n}{b} \times l = \frac{1}{2} \times f(n) < c \times f(n)
  \ for\ any\ c<\frac{1}{2}
\end{split}
$$

The algorithm is $\Theta(f(n)) = \Theta(n)$.

## Question 6 - Highest Safe Rung

I could start from the bottom and keep dropping the jar from a rung $m$ rungs higher than the prvious one and then do a linear search btween the point where the first jar broke and last successful drop.

In the worst case senario the running time would be

$$
\begin{split}
  T(n) = \frac{n}{m}+m = o(n)
  \\
  T(n) = \frac{n}{m}+m < n
\end{split}
$$

Since I know that the binary search method yeilds the fastest result and is $\log{n}$ then the new solution can't be as fast as that but we know $\log{n} < \sqrt{n} < n$ so we need a rlationship btween $m$ and $n$ such that $T(n) = \sqrt{n}$.

$$
\begin{split}
  \frac{n}{m}+m = c \times \sqrt{n}, \ for\ a\ big\ enough\ n
  \\
  \frac{\sqrt{n}}{m} + \frac{m}{\sqrt{n}} = c
  \\
  \frac{n + m^2}{m \times \sqrt{n}} = c
  \\
  n + m^2 = c \times m \times \sqrt{n}
  \\
  n - c \times m \times \sqrt{n} + m^2 = 0
  \\
  (\sqrt{n}-m)^2 =  0
  \\
  \sqrt{n}-m = 0
  \\
  m = \sqrt{n}
\end{split}
$$

Now that we know what $m$ needs to be here is the algorithm:

```py
def highestSafeRung():
  rung = 0
  while rung<n:
    if drop(rung)==broken:
      break # get out of the loop if the jar broke
    rung += math.sqrt(n)
  for i in range(rung-math.sqrt(n),rung):
    if drop(rung)==broken:
      return i-1
```

### General Case for $k$ jars

Now if I wanted to use $k$ jars instead of $2$ I could make the algorithm faster by further segmenting the ladder. I figured the algorithm should be faster than the one in part A but not as fast as binary search tree so I am thinking if I could segment the rungs so that algoithm is in the order of $n^{\frac{1}{k}}$ it would satisfy that condition meaning ( $\log{n} < n^{\frac{1}{k}} < \sqrt{n}$ ).

I can try to do a similar calcuation for a new $m$.

$$
\begin{split}
  \frac{n}{m}+m = c \times n^{\frac{1}{k}}, \ for\ a\ big\ enough\ n
  \\
  \frac{n^{\frac{1}{k}}}{m} + \frac{m}{n^{\frac{1}{k}}} = c
  \\
  \frac{n^{\frac{2}{k}} + m^2}{m \times n^{\frac{1}{k}}} = c
  \\
  n^{\frac{2}{k}} + m^2 = c \times m \times n^{\frac{1}{k}}
  \\
  n^{\frac{2}{k}} - c \times m \times n^{\frac{1}{k}} + m^2 = 0
  \\
  (n^{\frac{1}{k}}-m)^2 =  0
  \\
  n^{\frac{1}{k}}-m = 0
  \\
  m = n^{\frac{1}{k}}
\end{split}
$$

So I can just do the same algorithm but increment the rung by $n^{\frac{1}{k}}$ instead of $\sqrt{n}$.

```py
def highestSafeRung(k):
  rung = 0
  increment = n ** (1/k)
  while rung<n:
    if drop(rung)==broken:
      break # get out of the loop if the jar broke
    rung += increment
  for i in range(rung-increment,rung):
    if drop(rung)==broken:
      return i-1
```

Now with this algorithm in the worst case senario we use k jars and teh run time analysis is $\Theta(n^{\frac{1}{k}})$.
