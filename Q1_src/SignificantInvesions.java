import java.util.ArrayList;
import java.util.List;

class SignificantInvesions {

  private static List<String> significantInversionPairs = new ArrayList<>();

  public static class Result {
    int[] array;
    int inversionCount;
    
    public Result(int[] array, int inversionCount) {
      this.array = array;
      this.inversionCount = inversionCount;
    }
    
    public int[] getArray() {
      return array;
    }
    
    public int getInversionCount() {
      return inversionCount;
    }
    
    public void setArray(int[] array) {
      this.array = array;
    }
    
    public void setInversionCount(int inversionCount) {
      this.inversionCount = inversionCount;
    }
  }
  
  public static Result numSignificantInversions(int[] a, int start, int end) {
    

    if (end - start < 2) {  // Base case: one or zero elements
      return new Result(a, 0);
    }
    else {
      int mid = (start + end) / 2;
      
      // Recursively count inversions in left and right halves
      Result leftResult = numSignificantInversions(a, start, mid);
      Result rightResult = numSignificantInversions(a, mid, end);

      // Merge both halves and count inversions across the halves
      Result mergeResult = merge(a, start, mid, end);

      // Sum all inversions
      int totalInversions = leftResult.inversionCount + rightResult.inversionCount + mergeResult.inversionCount;
      return new Result(a, totalInversions);
    }
  }

  private static void recordSignificantInversion(int leftValue, int rightValue) {
    significantInversionPairs.add("(" + leftValue + ", " + rightValue + ")");
  }

  public static Result merge(int[] array, int start, int mid, int end) {
      int num = 0;
      int i = start;
      int j = mid;
      
      // Temporary array for merging sorted halves
      int[] temp = new int[end - start];
      int tempIndex = 0;

      // Count significant inversions where elements in the left half are > 2 * elements in the right half
      while (i < mid && j < end) {
        if (array[i] > 2 * array[j]) {
          num += mid - i;
          for (int k = i; k < mid; k++) {
            recordSignificantInversion(array[k], array[j]);
          }
          j++;
        }
        else {
          i++;
        }
      }

      // Reset indices for merging process
      i = start;
      j = mid;

      // Merge the sorted halves into temp array
      while (i < mid && j < end) {
        if (array[i] <= array[j]) {
          temp[tempIndex++] = array[i++];
        }
        else {
          temp[tempIndex++] = array[j++];
        }
      }

      // Copy remaining elements from the left half, if any
      while (i < mid) {
        temp[tempIndex++] = array[i++];
      }

      // Copy remaining elements from the right half, if any
      while (j < end) {
        temp[tempIndex++] = array[j++];
      }

      // Copy sorted temp array back into the main array
      System.arraycopy(temp, 0, array, start, temp.length);

      return new Result(array, num);
    }

  public static void main(String[] args){
    int[] a = new int[args.length];
    for (int i = 0; i < args.length; i++) {
      a[i] = Integer.parseInt(args[i]);
    }

    Result result = numSignificantInversions(a, 0, a.length);
    System.out.println("Number of Significant Inversions: " + result.getInversionCount());

    System.out.println("All Significant Inversions:");
    for (String pair : significantInversionPairs) {
      System.out.println(pair);
    }
  }
}