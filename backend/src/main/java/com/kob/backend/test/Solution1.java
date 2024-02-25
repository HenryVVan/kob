package com.kob.backend.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：Henry Wan
 * @Package：com.kob.backend.test
 * @Project：backend
 * @Date：2024/2/21 12:22
 * @Filename：Solution
 */
public class Solution1 {

}
class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        // 构造前缀和同时把和和下标存入map
        int n = nums.length;
        int[] preSum = new int[n + 1];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            preSum[i + 1] = sum;
            List<Integer> list = map.getOrDefault(sum, new ArrayList<>());
            list.add(i);
            map.put(sum, list);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int need = k + preSum[i];
            if (map.containsKey(need)){
                List<Integer> list = map.get(need);
                for (int j : list) {
                    if (j > i) ans = Math.max(j - i, ans);
                }
            }
        }
        return ans;
    }
}