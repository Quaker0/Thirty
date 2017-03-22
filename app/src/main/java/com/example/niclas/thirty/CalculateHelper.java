package com.example.niclas.thirty;

import android.util.Log;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Niclas on 2017-01-24.
 * A helper function to GameActivity
 */

public class CalculateHelper {
    Model model;
    GameActivity game;

    public CalculateHelper(final GameActivity game, final Model model) {
        this.model=model;
        this.game = game;
    }


    /**
     * Calculate the results for the throws and show them in separate activity.
     */
    public ArrayList<Integer> calculateResult() {
        String spinner = game.getSpinnerValue();

        int[] dices = model.getDiceResult();
        Log.d("Dice List ",model.arrayToList(dices).toString());

        if (model.getTurnResults().size() >= 3) {
            model.resetTurnResults();
        }
        if (model.getTurnResults().size() >= 10) {
            model.resetAllResults();
        }

        switch(spinner) {
            case "Low":
                int res = 0;
                for (int d:dices) {
                    if(d<4) {
                        res += d;
                    }
                }
                model.addTurnResult(res);
                break;
            default:
                Log.d("Spinner",spinner);
                int result = calcResult( Integer.parseInt(spinner), model.arrayToList(dices) );
                Log.d("Result",result+"");
                model.addTurnResult(result);
        }
        model.addResult(add(model.getTurnResults()));
        Log.d("Results: ",model.getResults().toString());
        return model.getResults();
    }



    private int add(ArrayList<Integer> arr) {
        int res = 0;
        for (int i : arr) {
            res+=i;
        }
        return res;
    }

    /**
     * Calculate the result of dices thrown according to Thirty rules.
     * Count multiples of &val and count total
     * @param val
     * @param dices
     * @return
     */
    private static int calcResult(int val, List<Integer> dices) {
        Collections.sort(dices);
        List<Integer> done = new ArrayList<Integer>();

        //Check multiples of 1 and 2
        Multiple m = calc2Mult(val,dices,done);

        //Check multiples of 3
        m = calc3Mult(val,m.getDices(),m.getDone());

        //Check multiples of 4
        m = calc4Mult(val,m.getDices(),m.getDone());

        dices=m.getDices();
        done=m.getDone();
        int sum = sum(dices);

        //Check multiples of 5
        if(sum>val) {
            Integer diff = sum-val;
            if(dices.contains(diff)) { //exists
                List<Integer> excludeDiff = dices;
                excludeDiff.remove(diff);
                done.addAll(excludeDiff);
                dices.removeAll(excludeDiff);
            }
        }
        //Check multiples of 6
        else if(sum==val) {
            done.addAll(dices);
            dices = new ArrayList<Integer>();
        }
        return sum(done);
    }


    /**
     * Calculate multiples of 1 and 2
     * @param val
     * @param dices
     * @param done
     * @return
     */
    private static Multiple calc2Mult(int val, List<Integer> dices, List<Integer> done) {
        //If value too high don't bother (for speed), or list too short.
        if(dices.size()<2 || dices.get(dices.size()-1)+dices.get(dices.size()-2)<val) {
            return new Multiple(dices,done);
        }

        List<Integer> temp = new ArrayList<Integer>();
        for (int i=dices.size()-1;i>=0;i--) {
            if (dices.get(i) == val) {
                done.add(dices.get(i));
                dices.remove(i);
            } else if (i>0) {
                for (int j=i-1; j>=0; j--) {
                    if (dices.get(i)+dices.get(j) == val  && !temp.contains(dices.get(i))
                            && !temp.contains(dices.get(j))) {
                        temp.add(dices.get(i));
                        temp.add(dices.get(j));
                        break;
                    } else if(dices.get(i)+dices.get(j) < val) {
                        //Save time
                        break;
                    }
                }
            }
        }
        for(Integer t : temp) {
            dices.remove(t);
        }
        done.addAll(temp);
        return new Multiple(dices,done);
    }

    /**
     * Check multiples of 3
     * @param val
     * @param dices
     * @param done
     * @return
     */
    private static Multiple calc3Mult(int val, List<Integer> dices, List<Integer> done) {
        //If value too high don't bother (for speed), or size too short (incompatible)
        if(dices.size()<3 || dices.get(dices.size()-1)+dices.get(dices.size()-2)+dices.get(dices.size()-3)<val) {
            return new Multiple(dices,done);
        }
        List<Integer> temp = new ArrayList<Integer>();
        for (int i=dices.size()-1;i>1;i--) {
            for (int j=i-1; j>0; j--) {
                for(int k=j-1; k>=0; k--) {
                    if (dices.get(i)+dices.get(j)+dices.get(k) == val && !temp.contains(dices.get(i))
                            && !temp.contains(dices.get(j)) && !temp.contains(dices.get(k))) {
                        temp.add(dices.get(i));
                        temp.add(dices.get(j));
                        temp.add(dices.get(k));

                        break;
                    } else if(dices.get(i)+dices.get(j)+dices.get(k) < val) {
                        //Save time
                        break;
                    }
                }
            }
        }
        for(Integer t : temp) {
            dices.remove(t);
        }
        done.addAll(temp);
        return new Multiple(dices,done);
    }

    /**
     * Check multiples of 4
     * @param val
     * @param dices
     * @param done
     * @return
     */
    private static Multiple calc4Mult(int val, List<Integer> dices, List<Integer> done) {
        //If value too high don't bother (for speed), or size too short (incompatible)
        if(dices.size()<4 || dices.get(dices.size()-1)+dices.get(dices.size()-2)+dices.get(dices.size()-3)+dices.get(dices.size()-4)<val) {
            return new Multiple(dices,done);
        }

        List<Integer> temp = new ArrayList<Integer>();
        for (int i=dices.size()-1;i>2;i--) {
            for (int j=i-1; j>1; j--) {
                for (int k=j-1; k>0; k--) {
                    for(int l=k-1; l>=0; l--) {
                        if (dices.get(i)+dices.get(j)+dices.get(k)+dices.get(l) == val && !temp.contains(dices.get(i))
                                && !temp.contains(dices.get(j)) && !temp.contains(dices.get(k))  && !temp.contains(dices.get(l))) {
                            temp.add(dices.get(i));
                            temp.add(dices.get(j));
                            temp.add(dices.get(k));
                            temp.add(dices.get(l));
                            break;
                        } else if(dices.get(i)+dices.get(j)+dices.get(k)+dices.get(l) < val) {
                            //Save time
                            break;
                        }
                    }
                }
            }
        }
        for(Integer t : temp) {
            dices.remove(t);
        }
        done.addAll(temp);
        return new Multiple(dices,done);
    }

    /**
     * Calculate sum of a list
     * @param list
     * @return
     */
    public static Integer sum(List<Integer> list) {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }
}

/**
 * A multiple class to store two lists of integers, one for unprocessed dices
 * and one for dices to be included in the result.
 */
class Multiple {
    private List<Integer> mDices;
    private List<Integer> mDone;

    public Multiple(List<Integer> dices, List<Integer> done) {
        this.mDices = dices;
        this.mDone = done;
    }

    public List<Integer> getDices() {
        return mDices;
    }

    public List<Integer> getDone() {
        return mDone;
    }
}
