import java.awt.Rectangle;

import javax.swing.*;

public class RangeSlider extends JSlider implements _RangeSlider {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int minRange;
	private int min;
	private int maxRange;
	private int max;
	
	
	public RangeSlider(int min, int max) {
		this.min = min;
		this.minRange = min;
		this.max = max;
		this.maxRange = max;
	}
	
	
	@Override
	public int getMinRange() {
		// TODO Auto-generated method stub
		return this.minRange;
	}
	@Override
	public void setMinRange(int minRange) {
		// TODO Auto-generated method stub
		this.minRange = minRange;
	}
	@Override
	public int getMaxRange() {
		// TODO Auto-generated method stub
		return this.maxRange;
	}
	@Override
	public void setMaxRange(int maxRange) {
		// TODO Auto-generated method stub
		this.maxRange = maxRange;
	}
	@Override
	public int setValue() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getMin() {
		// TODO Auto-generated method stub
		return this.min;
	}
	@Override
	public void setMin(int min) {
		// TODO Auto-generated method stub
		this.min = min;
	}
	@Override
	public int getMax() {
		// TODO Auto-generated method stub
		return this.max;
	}
	@Override
	public void setMax(int max) {
		// TODO Auto-generated method stub
		this.max = max;
	}


}

