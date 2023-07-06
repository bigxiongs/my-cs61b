public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> queue = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            queue.addLast(word.charAt(i));
        }
        return queue;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> queue = wordToDeque(word);
        while (queue.size() > 1) {
            if (queue.removeFirst().equals(queue.removeLast())) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> queue = wordToDeque(word);
        while (queue.size() > 1) {
            if (cc.equalChars(queue.removeFirst(), queue.removeLast())) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
