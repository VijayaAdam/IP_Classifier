import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class URLorIPFinder extends JFrame {

    private JTextField inputField;
    private JLabel outputLabel;

    public URLorIPFinder() {
        super("URL or IP Finder");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton urlButton = new JButton("Enter Website URL");
        JButton ipButton = new JButton("Enter IP Address");

        urlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel urlPanel = new JPanel();
                urlPanel.setLayout(new FlowLayout());

                inputField = new JTextField();
                inputField.setColumns(20);
                urlPanel.add(inputField);

                JButton enterButton = new JButton("Enter");
                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        String url = inputField.getText();
                        try {
                            InetAddress address = InetAddress.getByName(url);
                            String hostname = address.getHostName();
                            String ipAddress = address.getHostAddress();
                            String ipClass = getIPClass(ipAddress);
                            outputLabel = new JLabel("Hostname: " + hostname + ", IP Address: " + ipAddress + ", IP Class: " + ipClass);
                            urlPanel.add(outputLabel);
                            pack();
                        } catch (UnknownHostException ex) {
                            outputLabel = new JLabel("Invalid URL or IP Address");
                            urlPanel.add(outputLabel);
                            pack();
                        }
                    }
                });
                urlPanel.add(enterButton);
                panel.add(urlPanel);
                pack();
            }
        });

        ipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel ipPanel = new JPanel();
                ipPanel.setLayout(new FlowLayout());

                inputField = new JTextField();
                inputField.setColumns(20);
                ipPanel.add(inputField);

                JButton enterButton = new JButton("Enter");
                enterButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        String ipAddress = inputField.getText();
                        String ipClass = getIPClass(ipAddress);
                        outputLabel = new JLabel("IP Class: " + ipClass);
                        ipPanel.add(outputLabel);
                        pack();
                    }
                });
                ipPanel.add(enterButton);
                panel.add(ipPanel);
                pack();
            }
        });

        panel.add(urlButton);
        panel.add(ipButton);

        add(panel, BorderLayout.CENTER);
        setSize(300, 200);
        setVisible(true);
    }

    private String getIPClass(String ipAddress) {
        String[] parts = ipAddress.split("\\.");
        int firstOctet = Integer.parseInt(parts[0]);

        if (firstOctet < 128) {
            return "A";
        } else if (firstOctet < 192) {
            return "B";
        } else if (firstOctet < 224) {
            return "C";
        } else if (firstOctet < 240) {
            return "D";
        } else {
            return "E";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new URLorIPFinder();
            }
        });
    }
}