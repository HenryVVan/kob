package com.example;

import javafx.util.Pair;

import java.util.*;


public class Solution {

}

/*class Solution {
    public long countSubarrays(int[] nums, int k) {
        int n = nums.length;
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, nums[i]);
        }
        long ans = 0;
        int[] f = new int[n + 1];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == max)
                f[++idx] = i;
        }
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] == max) {
                cnt++;
                System.out.println(f[cnt + k - 1]);
                if (f[cnt + k - 1] != 0) ans += n - f[cnt + k - 1];
            } else {
                System.out.println(f[cnt + k]);
                if (f[cnt + k] != 0) ans += n - f[cnt + k];
            }
        }
        return ans;
    }
}*/
/*class Solution {
    public List<Integer> getGoodIndices(int[][] variables, int target) {
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < variables.length; i++) {
            if (Math.pow(Math.pow(variables[i][0], variables[i][1]) % 10, variables[i][2]) % variables[i][3] == target)
            ans.add(i);
        }
        return ans;
    }
}*/

/*
class Solution {
    public int countTestedDevices(int[] batteryPercentages) {
        int ans = 0, max = 0, n = batteryPercentages.length;
        for (int i = 0; i < n; i++) {
            if (batteryPercentages[i] > 0 && batteryPercentages[i] > max) {
                ans ++;
                max ++;
            }
        }
        return ans;
    }
}
*/

/*
class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        HashMap<Integer, Integer> hm = new HashMap<>();
        int n = nums.length, ans = 0, idx = 0;
        for (int i = 0; i < n; i++) {
            int cnt = hm.getOrDefault(nums[i], 0) + 1;
            hm.put(nums[i], cnt);
            if (cnt <= k) {
                ans = Math.max(ans, i - idx + 1);
            }
            else {
                while (cnt > k) {
                    hm.put(nums[idx], hm.get(nums[idx]) - 1);
                    if (nums[idx] == nums[i]) cnt --;
                    idx ++;
                }
            }
        }
        return ans;
    }
}
*/

/*class Solution {
    public int removeAlmostEqualCharacters(String word) {
        char[] wds = word.toCharArray();
        int ans = 0;
        for (int i = 1; i < wds.length; i ++) {
            if (Math.abs(wds[i] - wds[i - 1]) <= 1) {
                ans++;
                i++;
            }
        }
        return ans;
    }
}*/


/*
class Solution {
    public int maximumStrongPairXor(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int sub = Math.abs(nums[i] - nums[j]);
                if (sub <= nums[i] && sub <= nums[j]) ans ++;
            }
        }
        return ans;
    }
}
*/

/*class Solution {
    public int[] findIntersectionValues(int[] nums1, int[] nums2) {
        int[] c1 = new int[101], c2 = new int[101];
        for (int i = 0; i < nums1.length; i++) {
            c1[nums1[i]]++;
        }
        for (int i = 0; i < nums2.length; i++) {
            c2[nums2[i]]++;
        }
        int m = 0, n = 0;
        for (int i = 0; i < nums1.length; i++) {
            if (c2[nums1[i]] != 0) m++;
        }
        for (int i = 0; i < nums2.length; i++) {
            if (c1[nums2[i]] != 0) n++;
        }
        return new int[]{m, n};
    }
}*/

/*
class Solution {
    public long distributeCandies(int n, int limit) {
        if (n > 3 * limit) return 0;
        long ans = 0L;
        for (int i = 0; i <= limit; i++) {
            int res = n - i;

        }
        return ans;
    }
}
*/



/*

    public static void main(String[] args) {
        int[][] a = new int[3][];
        a[0] = new int[]{0,1};
        a[1] = new int[]{1, -1};
        a[2] = new int[]{2, 0};
        Arrays.sort(a, (int[] o, int[] b) -> o[1] - b[1]);
        System.out.println(Arrays.toString(a[1]));
    }

class Solution {
    public int findChampion(int n, int[][] edges) {
        int m = edges.length;
        HashSet<Integer> hs = new HashSet<>();
        for (int i = 0; i < n; i++) {
            hs.add(i);
        }
        for (int i = 0; i < m; i++) {
            if (hs.contains(edges[i][1])) hs.remove(edges[i][1]);
        }
        int ans = 0;
        for (int h : hs) {
            ans = h;
            break;
        }
        return hs.size() > 1 ? -1 : ans;
    }
}

*/








/*
class Solution {
    public int findChampion(int[][] grid) {
        int n = grid.length;
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            cnt = 0;
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) cnt ++;
            }
            if (cnt == 1) return i;
        }
        return -1;
    }
}
*/


/*
class Solution {
    public long minIncrementOperations(int[] nums, int k) {
        int n = nums.length;
        long[] f = new long[n];
        Arrays.fill(f, -1);
        for (int i = 0; i < 3; i++) {
            if (nums[i] >= k) f[i] = 0;
            else f[i] = k - nums[i];
        }
        for (int i = 3; i < n; i ++) {
            // 判断i是从 i-1 i-2 还是i-3转移而来
            for (int j = i - 2; j < i; j ++) {
                if (f[i] == -1) {
                    f[i] = f[j] + nums[i] >= k ? 0 : k - nums[i];
                }
                else {
                    f[i] = Math.min(f[i], f[j] + nums[i] >= k ? 0 : k - nums[i]);
                }
            }
        }
        return f[n - 1];
    }
}
*/



/*

class Solution {
    // 类似于定长的滑动队列
    Deque<Pair<Integer, Integer>> dq;

    public long minIncrementOperations(int[] nums, int k) {
        this.dq = new LinkedList<>();
        long ans = 0;
        for (int i = 0; i < 3; i++) {
            offer(i, nums[i]);
        }
        ans += dq.getFirst().getValue() >= k ? 0 : k - dq.getFirst().getValue();
        dq.offerFirst(new Pair<>(dq.pollFirst().getKey(), k));
        for (int i = 3; i < nums.length; i++) {
            poll(i - 3);
            offer(i, nums[i]);
            ans += dq.getFirst().getValue() >= k ? 0 : k - dq.getFirst().getValue();
        }
        return ans;
    }

    // 从后往前，比当前的小就删除
    void offer(int idx, int val) {
        while (!dq.isEmpty() && dq.getLast().getValue() <= val) {
            dq.pollLast();
        }
        dq.offer(new Pair<>(idx, val));
    }

    // 如果当前队顶的元素等于数组要滑动出去的元素就删掉
    void poll(int idx) {
        if (!dq.isEmpty() && dq.getFirst().getKey() == idx) dq.pollFirst();
    }

}
*/


/*

class Solution {
    public long minSum(int[] nums1, int[] nums2) {
        long s1 = 0, s2 = 0; // 记录非0和
        int cnt1 = 0, cnt2 = 0; // 记录0的数量
        int n1 = nums1.length, n2 = nums2.length;
        for (int i = 0; i < n1; i++) {
            if (nums1[i] == 0) cnt1 ++;
            else s1 += nums1[i];
        }
        for (int i = 0; i < n2; i++) {
            if (nums2[i] == 0) cnt2 ++;
            else s2 += nums2[i];
        }
        // 如果两者不可以修改
        if (cnt1 == 0 && cnt2 == 0) return s1 == s2 ? s1 : -1;
        else if (cnt1 == 0) {
            return s1 >= s2 + cnt2 ? s1 : -1;
        }
        else if (cnt2 == 0) {
            return s2 >= s1 + cnt1 ? s1 : -1;
        }
        else {
            return Math.max(s1 + cnt1, s2 + cnt2);
        }
    }
}
*/


/*
class Solution {
    public int findKOr(int[] nums, int k) {
        int ans = 0, cnt = 0;
        for (int i = 0; i < 32; i++) {
            cnt = 0;
            for (int j = 0; j < nums.length; j++) {
                if ((1 << i & nums[j]) != 0) cnt ++;
            }
            if (cnt >= k) ans += Math.pow(2, i);
        }
        return ans;
    }
}
*/
