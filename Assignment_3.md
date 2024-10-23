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

Acorrding to the master theorem since:

$$
\begin{split}
  f(n) = \Theta(n \times \log^{k}{n}) = \Theta(n^{\log_{b}{a}} \times \log^{k}{n})\ for\ k=0
\end{split}
$$

The algorithm is $\Theta(f(n)) = \Theta(n)$.

## Question 2 - Still Point Algorithm

My algorithm returns $-1$ for the when there are no indexes that $a_i = i$ cause what if $a_0 = 0$.

```py
# Unfortunatly I can't do operations with just one Array as a paremeter
# because doing A[:mid] or A[mid:] changes the indexes
def stillpoint(A, low, high):
  if low > high:
    return -1
  mid = (low + high)/2
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
  mid = (low + high)/2
  if (mid == 0 or arr[mid-1] <= arr[mid]) and (mid == (len(arr)-1) or arr[mid]>=arr[mid+1]):
    return mid
  elif mid > 0 and arr[mid-1] > arr[mid]:
    return findPeak(arr, low, mid)
  else:
    return findPeak(arr, mid, hight)
```
