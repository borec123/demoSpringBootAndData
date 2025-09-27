package com.borec.backend.stopwatch.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.borec.backend.stopwatch.StopWatch;
import com.borec.backend.stopwatch.StopWatchListener;

import cz.borec.stopwatch.util.TimeUtils;

/**
 * GUI main window.
 * @deprecated Use {@link com.borec.backend.stopwatch.gui.Main } instead.
 */
public class SwingWindow implements StopWatchListener {

	StopWatch stopWatch = StopWatch.createInstance();
	JLabel stopWatchValue;
	JButton startButton;
	JButton stopButton;
	JButton resetButton;

	private void setStarted(boolean b) {
		startButton.setEnabled(!b);
		stopButton.setEnabled(b);
		resetButton.setEnabled(!b);
	}

	public static void main(String[] args) {
		new SwingWindow().createWindow();
	}

	private void createWindow() {
		JFrame frame = new JFrame("Stopwatch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createUI(frame);
		frame.setSize(560, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		setStarted(false);
		stopWatch.addStopWatchListener(this);
	}

	private void createUI(final JFrame frame) {
		JPanel panel = new JPanel();
		LayoutManager layout = new FlowLayout();
		panel.setLayout(layout);

		stopWatchValue = new JLabel("00:00:00.000");
		stopWatchValue.setFont(new Font(stopWatchValue.getFont().getName(), stopWatchValue.getFont().getStyle(), 25));
		// stopWatchValue.setBorder(new LineBorder(Color.BLACK));

		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		resetButton = new JButton("Reset");

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopWatch.start();
				setStarted(true);
			}
		});

		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopWatch.stop();
				setStarted(false);
			}
		});

		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopWatch.reset();
			}
		});

		frame.getRootPane().setDefaultButton(startButton);

		panel.add(startButton);
		panel.add(stopButton);
		panel.add(resetButton);

		JPanel north = new JPanel();
		JLabel elapsedLabel = new JLabel("Elapsed time:     ");
		elapsedLabel.setFont(new Font(elapsedLabel.getFont().getName(), elapsedLabel.getFont().getStyle(), 13));
		north.add(elapsedLabel);
		JPanel center = new JPanel();
		center.setLayout(new FlowLayout());
		center.add(stopWatchValue);
		frame.getContentPane().add(north, BorderLayout.NORTH);
		frame.getContentPane().add(center, BorderLayout.CENTER);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
	}

	@Override
	public void onTimeChangedIn100MillisecondsInterval(long time) {
		stopWatchValue.setText(TimeUtils.displayTime(time));
	}
}