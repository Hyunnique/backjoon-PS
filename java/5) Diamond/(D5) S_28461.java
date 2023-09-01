import java.io.*;
import java.util.*;

/**
 * @author Hyunnique
 * @since 2023. 08. 30.
 * @see BoJ 28461 (D5)
 * @category Implement
 * @note WHY DON'T YOU JUST GET UP AND DANCE MAN?
 *
 * 빡구현
 * 입력을 PQ를 이용해 시간순으로 정렬하고, Treemap을 이용해 좌표압축
 * 이후 롱노트는 롱노트의 갯수, 누른 갯수의 누적합을 이용해서 판정을 진행하고
 * 숏노트는 Queue를 이용해서 판정 진행
 * "먼저 나온 일반노트 혹은 롱노트의 판정이 발생하기 전까지 그 뒤의 노트들은 판정이 발생하지 않음" 에 유의하여
 * 발판을 눌렀을 때 판정을 진행할 일반노트와 발판을 밟은 시점 사이에 롱노트가 존재할 경우
 * 일반노트의 판정을 하지 않아야 함을 유의해서 구현
 * 
 */

class Notegroup {
    long time;
    int judgedCount, minJudge, noteCount;
    int longnoteCount = 0;
    boolean longnoteJudged, judgeCompleted;

    public Notegroup(long time) {
        this.time = time;

        this.judgedCount = 0;
        this.minJudge = 0;
        this.noteCount = 0;
        this.longnoteJudged = false;
        this.judgeCompleted = false;
    }

    @Override
    public String toString() {
        return "[t=" + time + ", judgeCompleted=" + judgeCompleted + ", longnoteCount=" + longnoteCount + "]";
    }
}

class Longnote {
    int buttonIndex;
    long startTime, endTime;

    public Longnote(int buttonIndex, long startTime, long endTime) {
        this.buttonIndex = buttonIndex;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

class Note {
    int buttonIndex;
    long time;
    boolean isLongnotePlaceholder = false;

    public Note(int buttonIndex, long time) {
        this.buttonIndex = buttonIndex;
        this.time = time;
    }
}

class Button {
    //int index;
    boolean pressed = false;
    boolean lnEnabled = false;
    long lnEndTime = 0;

    Queue<Note> noteQueue = new ArrayDeque<>();
}

class Judge implements Comparable<Judge> {
    int eventType, buttonIndex;
    long time;
    long endTime;

    public Judge(int eventType, int buttonIndex, long time) {
        this.eventType = eventType;
        this.buttonIndex = buttonIndex;
        this.time = time;
    }

    @Override
    public int compareTo(Judge o) {
        if (time == o.time) {
            return Integer.compare(eventType, o.eventType);
        } else {
            return Long.compare(time, o.time);
        }
    }

    public String evtTypeToString() {
        if (eventType == 0) return "LONGNOTE_STARTED";
        else if (eventType == 1) return "BUTTON_PRESSED";
        else if (eventType == 2) return "LONGNOTE_JUDGE";
        else if (eventType == 3) return "LONGNOTE_ENDED";
        else if (eventType == 4) return "BUTTON_RELEASED";
        else if (eventType == 5) return "NOTE_STARTED";
        else return "";
    }

    @Override
    public String toString() {
        return "[eventType=" + evtTypeToString() + ", buttonIndex=" + buttonIndex + ", time=" + time + "]";
    }
}

class JudgeResult implements Comparable<JudgeResult> {
    long targetTime, judgeTime;
    int judgeType;

    JudgeResult(long judgeTime, long targetTime, int judgeType) {
        this.judgeTime = judgeTime;
        this.targetTime = targetTime;
        this.judgeType = judgeType;
    }

    @Override
    public int compareTo(JudgeResult o) {
        if (judgeTime == o.judgeTime) {
            return Long.compare(targetTime, o.targetTime);
        } else {
            return Long.compare(judgeTime, o.judgeTime);
        }
    }
}

public class PIU {
    static BufferedScanner sc = new BufferedScanner();

    static final int EVENTTYPE_LONGNOTE_STARTED = 0;
    static final int EVENTTYPE_BUTTON_PRESSED = 1;
    static final int EVENTTYPE_LONGNOTE_JUDGE = 2;
    static final int EVENTTYPE_LONGNOTE_ENDED = 3;
    static final int EVENTTYPE_BUTTON_RELEASED = 4;
    static final int EVENTTYPE_NOTE_STARTED = 5;

    static final int JUDGEMENT_PERFECT = 0;
    static final int JUDGEMENT_GREAT = 1;
    static final int JUDGEMENT_GOOD = 2;
    static final int JUDGEMENT_BAD = 3;
    static final int JUDGEMENT_MISS = 4;

    static class Game {
        // 판정 결과 Stringbuilder
        StringBuilder sb = new StringBuilder();

        Button[] buttons; // 발판 배열
        long[] judgementRange; // 판정 범위
        TreeMap<Long, Notegroup> noteGroups = new TreeMap<>();
        ArrayList<Notegroup> indexedTimeline = new ArrayList<>();

        PriorityQueue<JudgeResult> judgeResultList = new PriorityQueue<>();

        int noteGroupIndexedCursor = 0;

        int perfect = 0, great = 0, good = 0, bad = 0, miss = 0;
        int combo = 0, missCombo = 0, maxCombo = 0;

        int longnoteHitSum = 0;
        boolean aborted = false;

        Game(int buttonCount, long[] judgementRange) {
            this.buttons = new Button[buttonCount];

            for (int i = 0; i < buttonCount; i++) {
                this.buttons[i] = new Button();
            }

            this.judgementRange = judgementRange;
        }

        public void judge(Judge j) {
            //sb.append("btn=" + j.buttonIndex + ", t=" + j.time).append("\n");
            //System.out.println(j.toString() + " judge long? " + judgeLongnote);

            if (aborted) return;

            //sb.append(j.toString()).append("\n");

            int judgeType = j.eventType;
            Button judgeButton = buttons[j.buttonIndex];
            long judgeTime = j.time;

            //sb.append(indexedTimeline.get(0)).append("\n");
            //sb.append("t=" + indexedTimeline.get(noteGroupIndexedCursor).time + "/" + indexedTimeline.size());
            while (true) {
                if (aborted) return;
                if (noteGroupIndexedCursor >= indexedTimeline.size()) break;
                Notegroup indexedGroup = indexedTimeline.get(noteGroupIndexedCursor);

                if (indexedGroup.time + judgementRange[3] < judgeTime && !indexedGroup.judgeCompleted) {
                    indexedGroup.judgeCompleted = true;
                    indexedGroup.minJudge = 4;

                    judgeResultList.offer(new JudgeResult(indexedGroup.time + judgementRange[3], indexedGroup.time, 4));
                    //applyJudge(4, judgeTime);
                }
                else if (indexedGroup.judgeCompleted) {
                    ;
                }
                else break;

                noteGroupIndexedCursor++;
            }

            while (true) {
                if (aborted) return;
                Note peek = judgeButton.noteQueue.peek();
                if (peek == null) break;

                if (peek.isLongnotePlaceholder) {
                    if (peek.time < judgeTime) {
                        judgeButton.noteQueue.poll();
                        continue;
                    }
                } else {
                    if (peek.time + judgementRange[3] < judgeTime) {
                        judgeButton.noteQueue.poll();
                        continue;
                    }
                }

                break;
            }

            switch (judgeType) {
                case EVENTTYPE_BUTTON_PRESSED:
                    judgeButton.pressed = true;

                    if (judgeButton.lnEnabled) {
                        longnoteHitSum++;
                    }

                    Note peek = judgeButton.noteQueue.peek();

                    // 만약 다음 일반노트가 없으면 리턴
                    if (peek == null) break;
                    // 현재 롱노트가 활성화되어있다면
                    // 다음 일반노트가 롱노트 전에 나타난 노트면 판정처리 해주고
                    // 후에 나타난 노트면 무시해줘야 한다
                    // **** 만약 롱노트가 끝나는 타이밍에 밟았다면 다음 일반노트 판정을 해줄 수 있지 않을까?
                    // -> 일단 25% 테케에는 영향이 없음

                    if (peek.isLongnotePlaceholder) break;
                    if ((judgeButton.lnEnabled && peek.time > judgeTime)) break;

                    Notegroup noteGroup = noteGroups.get(peek.time);
                    boolean noteJudged = false;
                    //sb.append("judge shortNote (line " + j.buttonIndex + ") pressed=" + judgeTime + ", peek=" + peek.time).append("\n");
                    for (int i = 0; i < 4; i++) {
                        if (peek.time - judgementRange[i] <= judgeTime && judgeTime <= peek.time + judgementRange[i]) {
                            noteGroup.minJudge = Math.max(noteGroup.minJudge, i);

                            if (++noteGroup.judgedCount == noteGroup.noteCount && (noteGroup.longnoteJudged || noteGroup.longnoteCount == 0) && !noteGroup.judgeCompleted) {
                                noteGroup.judgeCompleted = true;
                                judgeResultList.offer(new JudgeResult(judgeTime, noteGroup.time, noteGroup.minJudge));
                            }

                            noteJudged = true;
                            break;
                        }
                    }

                    if (noteJudged) judgeButton.noteQueue.poll();
                    break;

                case EVENTTYPE_LONGNOTE_STARTED:
                    judgeButton.lnEnabled = true;
                    judgeButton.lnEndTime = j.endTime;
                    if (judgeButton.pressed) longnoteHitSum++;
                    break;

                case EVENTTYPE_LONGNOTE_JUDGE:
                    judgeLong(judgeTime);
                    break;

                case EVENTTYPE_LONGNOTE_ENDED:
                    judgeButton.lnEnabled = false;
                    if (judgeButton.pressed) longnoteHitSum--;
                    break;

                case EVENTTYPE_BUTTON_RELEASED:
                    judgeButton.pressed = false;
                    if (judgeButton.lnEnabled) longnoteHitSum--;
                    break;
            }
        }

        private void judgeLong(long time) {
            Notegroup noteGroup = noteGroups.get(time);

            if (noteGroup != null) {
                //sb.append("judging");
                //sb.append("t=" + time + " enabled=" + noteGroup.longnoteCount + ", hit=" + longnoteHitSum);
                noteGroup.longnoteJudged = true;

                //System.out.println(longnoteHitSum + "/" + longnoteEnabledSum);
                if (longnoteHitSum < noteGroup.longnoteCount) {
                    noteGroup.judgeCompleted = true;
                    noteGroup.minJudge = 4;
                    judgeResultList.offer(new JudgeResult(time, noteGroup.time, noteGroup.minJudge));
                } else {
                    //System.out.println(judgeButton.noteQueue.size());
                    //System.out.println(noteGroup.judgedCount + "," + noteGroup.noteCount + "," + noteGroup.judgeCompleted);
                    if (noteGroup.judgedCount == noteGroup.noteCount && !noteGroup.judgeCompleted) {
                        noteGroup.judgeCompleted = true;
                        judgeResultList.offer(new JudgeResult(time, noteGroup.time, noteGroup.minJudge));
                    }
                }
            }
        }

        // 판정 결과를 처리하는 메소드
        private void applyJudge(int judgeIndex, long judgeTime) {
            if (aborted) return;

            switch (judgeIndex) {
                case JUDGEMENT_PERFECT:
                    ++perfect;
                    ++combo;
                    maxCombo = Math.max(maxCombo, combo);

                    missCombo = 0;
                    sb.append("PERFECT");
                    if (combo >= 4) sb.append(" ").append(String.format("%03d", combo));
                    sb.append("\n");
                    break;
                case JUDGEMENT_GREAT:
                    ++great;
                    ++combo;
                    maxCombo = Math.max(maxCombo, combo);

                    missCombo = 0;
                    sb.append("GREAT");
                    if (combo >= 4) sb.append(" ").append(String.format("%03d", combo));
                    sb.append("\n");
                    break;
                case JUDGEMENT_GOOD:
                    ++good;

                    missCombo = 0;
                    sb.append("GOOD");
                    if (combo >= 4) sb.append(" ").append(String.format("%03d", combo));
                    sb.append("\n");
                    break;
                case JUDGEMENT_BAD:
                    ++bad;
                    combo = 0;
                    missCombo = 0;
                    sb.append("BAD\n");
                    break;
                case JUDGEMENT_MISS:
                    combo = 0;
                    ++miss;
                    ++missCombo;

                    sb.append("MISS");
                    if (missCombo >= 4) sb.append(" ").append(String.format("%03d", missCombo));
                    sb.append("\n");

                    if (missCombo >= 51) {
                        sb.append("HEY!! WHY DON'T YOU JUST GET UP AND DANCE MAN?").append("\n");
                        aborted = true;
                        return;
                    }
                    break;
            }
        }

        // 결과 출력 메소드
        public void showResult() {
            if (!aborted) {
                sb.append("PERFECT ").append(String.format("%03d", perfect)).append("\n");
                sb.append("GREAT ").append(String.format("%03d", great)).append("\n");
                sb.append("GOOD ").append(String.format("%03d", good)).append("\n");
                sb.append("BAD ").append(String.format("%03d", bad)).append("\n");
                sb.append("MISS ").append(String.format("%03d", miss)).append("\n");
                sb.append("MAX COMBO ").append(String.format("%03d", maxCombo)).append("\n");
            }

            System.out.print(sb.toString().trim());
        }
    }

    public static void main(String[] args) throws IOException {

        // 발판의 개수
        int buttonCount = sc.nextInt();

        // 판정 범위 배열 (0 = Perfect ~ 3 = Bad)
        long[] judgementRange = new long[4];

        for (int i = 0; i < 4; i++) {
            judgementRange[i] = sc.nextLong();
        }

        // 발판과 판정 범위 초기화
        Game game = new Game(buttonCount, judgementRange);

        // 모든 이벤트를 담아둘 PQ
        PriorityQueue<Judge> judgementQueue = new PriorityQueue<>();

        // 1. 일반 노트 입력
        for (int line = 0; line < buttonCount; line++) {
            int noteCount = sc.nextInt();

            for (int i = 0; i < noteCount; i++) {
                Note n = new Note(line, sc.nextLong());

                Judge j = new Judge(EVENTTYPE_NOTE_STARTED, line, n.time);

                // 해당 시점에 노트그룹이 없다면 추가해주고, 롱노트 판정 시점 추가
                if (!game.noteGroups.containsKey(n.time)) {
                    Notegroup noteGroup = new Notegroup(n.time);
                    noteGroup.noteCount++;
                    game.noteGroups.put(n.time, noteGroup);

                    judgementQueue.offer(new Judge(EVENTTYPE_LONGNOTE_JUDGE, 0, n.time));
                } else {
                    game.noteGroups.get(n.time).noteCount++;
                }

                judgementQueue.offer(j);
            }
        }

        // 2. 롱 노트 입력
        for (int line = 0; line < buttonCount; line++) {
            int noteCount = sc.nextInt();

            for (int i = 0; i < noteCount; i++) {
                Longnote n = new Longnote(line, sc.nextLong(), sc.nextLong());

                // 롱노트 시작, 끝 이벤트 추가
                Judge j1 = new Judge(EVENTTYPE_LONGNOTE_STARTED, line, n.startTime);
                j1.endTime = n.endTime;
                Judge j2 = new Judge(EVENTTYPE_LONGNOTE_ENDED, line, n.endTime);

                // 해당 시점에 노트그룹이 없다면 추가해주고, 롱노트 판정 시점 추가
                if (!game.noteGroups.containsKey(n.startTime)) {
                    Notegroup noteGroup = new Notegroup(n.startTime);
                    game.noteGroups.put(n.startTime, noteGroup);

                    judgementQueue.offer(new Judge(EVENTTYPE_LONGNOTE_JUDGE, 0, n.startTime));
                }

                // 해당 시점에 노트그룹이 없다면 추가해주고, 롱노트 판정 시점 추가
                if (!game.noteGroups.containsKey(n.endTime)) {
                    Notegroup noteGroup = new Notegroup(n.endTime);
                    game.noteGroups.put(n.endTime, noteGroup);

                    judgementQueue.offer(new Judge(EVENTTYPE_LONGNOTE_JUDGE, 0, n.endTime));
                }

                judgementQueue.offer(j1);
                judgementQueue.offer(j2);
            }
        }

        // 3. 발판 이벤트 입력
        for (int line = 0; line < buttonCount; line++) {
            int hitCount = sc.nextInt();

            for (int i = 0; i < hitCount; i++) {
                Judge j1 = new Judge(EVENTTYPE_BUTTON_PRESSED, line, sc.nextLong());
                Judge j2 = new Judge(EVENTTYPE_BUTTON_RELEASED, line, sc.nextLong());

                judgementQueue.offer(j1);
                judgementQueue.offer(j2);
            }
        }

        int judgePoolSize = judgementQueue.size();
        int longnotePrefixSum = 0;

        PriorityQueue<Judge> gameQueue = new PriorityQueue<>();

        // 시간순으로 돌면서 다중노트 그룹에 현재 숏노트 갯수, 롱노트 누적 갯수 확인
        while (judgePoolSize-- > 0) {
            Judge j = judgementQueue.poll();
            Notegroup noteGroup = game.noteGroups.get(j.time);

            // 롱노트 판정 시점에 존재하는 롱노트의 갯수를 미리 저장해둠
            switch (j.eventType) {
                case EVENTTYPE_NOTE_STARTED:
                    game.buttons[j.buttonIndex].noteQueue.offer(new Note(j.buttonIndex, j.time));
                    break;
                case EVENTTYPE_LONGNOTE_STARTED:
                    Note n = new Note(j.buttonIndex, j.time);
                    n.isLongnotePlaceholder = true;
                    game.buttons[j.buttonIndex].noteQueue.offer(n);

                    longnotePrefixSum++;
                    break;
                case EVENTTYPE_LONGNOTE_JUDGE:
                    noteGroup.longnoteCount = longnotePrefixSum;
                    break;
                case EVENTTYPE_LONGNOTE_ENDED:
                    longnotePrefixSum--;
                    break;
            }

            gameQueue.offer(j);
        }

        // 좌표 압축된 타임라인을 배열로 변환 (인덱스로 접근)
        game.indexedTimeline.addAll(game.noteGroups.values());

        // 시간순으로 모든 이벤트에 대한 판정을 진행
        while (!gameQueue.isEmpty()) {
            if (game.aborted) break;
            Judge j = gameQueue.poll();
            game.judge(j);
        }

        // 미스처리하는 부분을 한번 더 불러주기 위함
        if (!game.aborted) game.judge(new Judge(EVENTTYPE_BUTTON_PRESSED, 0, Long.MAX_VALUE));

        // 판정 시점 -> 노트 등장 시점 순으로 정렬된 판정결과를 처리하고 출력
        while (!game.judgeResultList.isEmpty()) {
            JudgeResult r = game.judgeResultList.poll();
            if (game.aborted) break;
            //game.sb.append("judgeTime=" + r.judgeTime + ", peekTime=" + r.targetTime + " ");
            game.applyJudge(r.judgeType, r.judgeTime);
        }
        game.showResult();
        //System.out.println(test(game.showResult()));
    }
}