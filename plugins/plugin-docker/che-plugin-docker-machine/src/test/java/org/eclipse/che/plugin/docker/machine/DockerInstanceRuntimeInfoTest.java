/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *   Red Hat Inc.  - Add test cases
 *******************************************************************************/
package org.eclipse.che.plugin.docker.machine;

import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.Listeners;

@Listeners(MockitoTestNGListener.class)
public class DockerInstanceRuntimeInfoTest {
//    private static final String ALL_IP_ADDRESS           = "0.0.0.0";
//    private static final String CONTAINERINFO_GATEWAY    = "172.17.0.1";
//    private static final String CONTAINERINFO_IP_ADDRESS = "172.17.0.200";
//    private static final String DEFAULT_HOSTNAME         = "localhost";
//
//    @Mock
//    private ContainerInfo   containerInfo;
//    @Mock
//    private OldMachineConfig   machineConfig;
//    @Mock
//    private ContainerConfig containerConfig;
//    @Mock
//    private NetworkSettings networkSettings;
//
//    private DockerInstanceRuntime runtimeInfo;
//
//    @Mock
//    private static ServerEvaluationStrategyProvider provider;
//
//    @BeforeMethod
//    public void setUp() {
//
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                Collections.emptySet(),
//                                                Collections.emptySet());
//
//        when(containerInfo.getConfig()).thenReturn(containerConfig);
//        when(containerInfo.getNetworkSettings()).thenReturn(networkSettings);
//        when(networkSettings.getGateway()).thenReturn(CONTAINERINFO_GATEWAY);
//        when(networkSettings.getIpAddress()).thenReturn(CONTAINERINFO_IP_ADDRESS);
//        when(machineConfig.getServers()).thenReturn(Collections.emptyList());
//        when(containerConfig.getLabels()).thenReturn(Collections.emptyMap());
//
//        when(provider.get()).thenReturn(new DefaultServerEvaluationStrategy(null, null));
//    }
//
//    @Test
//    public void shouldReturnEnvVars() throws Exception {
//        // given
//        Map<String, String> expectedVariables = new HashMap<>();
//        expectedVariables.put("env_var1", "value1");
//        expectedVariables.put("env_var2", "value2");
//        expectedVariables.put("env_var3", "value3");
//
//        when(containerConfig.getEnv()).thenReturn(expectedVariables.entrySet()
//                                                                   .stream()
//                                                                   .map(stringStringEntry -> stringStringEntry.getKey() +
//                                                                                             "=" +
//                                                                                             stringStringEntry.getValue())
//                                                                   .collect(Collectors.toList())
//                                                                   .toArray(new String[expectedVariables.size()]));
//
//        // when
//        final Map<String, String> envVariables = runtimeInfo.getEnvVariables();
//
//        // then
//        assertEquals(envVariables, expectedVariables);
//    }
//
//    @Test
//    public void shouldReturnEmptyMapIfNoEnvVariablesFound() throws Exception {
//        when(containerConfig.getEnv()).thenReturn(new String[0]);
//
//        assertEquals(runtimeInfo.getEnvVariables(), Collections.emptyMap());
//    }
//
//    @Test
//    public void shouldReturnProjectsRoot() throws Exception {
//        final String projectsRoot = "/testProjectRoot";
//        final String[] envVars = {
//                "var1=value1",
//                "var2=value2",
//                DockerInstanceRuntime.PROJECTS_ROOT_VARIABLE + "=" + projectsRoot,
//                "var3=value3"
//        };
//        when(containerConfig.getEnv()).thenReturn(envVars);
//
//        assertEquals(runtimeInfo.projectsRoot(), projectsRoot);
//    }
//
//    @Test
//    public void shouldReturnNullProjectsRootIfNoAppropriateEnvVarFound() throws Exception {
//        final String[] envVars = {
//                "var1=value1",
//                "var2=value2",
//                "var3=value3"
//        };
//        when(containerConfig.getEnv()).thenReturn(envVars);
//
//        assertEquals(runtimeInfo.projectsRoot(), null);
//    }
//
//    @Test
//    public void shouldReturnServerForEveryExposedPort() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("100100/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                           .withHostPort("32101")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers.keySet(), ports.keySet());
//    }
//
//    @Test
//    public void shouldAddDefaultReferenceIfReferenceIsNotSet() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("100100/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                           .withHostPort("32101")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("8080/tcp", new OldServerImpl("OldServer-8080-tcp",
//                                                       null,
//                                                       CONTAINERINFO_GATEWAY + ":32100",
//                                                       null,
//                                                       new ServerPropertiesImpl(null, CONTAINERINFO_GATEWAY + ":32100", null)));
//        expectedServers.put("100100/udp", new OldServerImpl("OldServer-100100-udp",
//                                                         null,
//                                                         CONTAINERINFO_GATEWAY + ":32101",
//                                                         null,
//                                                         new ServerPropertiesImpl(null, CONTAINERINFO_GATEWAY + ":32101", null)));
//        expectedServers.put("8080/udp", new OldServerImpl("OldServer-8080-udp",
//                                                       null,
//                                                       CONTAINERINFO_GATEWAY + ":32102",
//                                                       null,
//                                                       new ServerPropertiesImpl(null, CONTAINERINFO_GATEWAY + ":32102", null)));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldAddRefUrlProtocolPathToServerFromMachineConfig() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        List<OldServerConfImpl> serversConfigs = new ArrayList<>();
//        doReturn(serversConfigs).when(machineConfig).getServers();
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("100100/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                           .withHostPort("32101")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        ports.put("8000/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32103")));
//        serversConfigs.add(new OldServerConfImpl("myserv1", "8080/tcp", "http", null));
//        serversConfigs.add(new OldServerConfImpl("myserv1-tftp", "8080/udp", "tftp", "/some/path"));
//        serversConfigs.add(new OldServerConfImpl("myserv2", "100100/udp", "dhcp", "/some"));
//        serversConfigs.add(new OldServerConfImpl(null, "8000/tcp", "tcp", "/path"));
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                Collections.emptySet(),
//                                                Collections.emptySet());
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("8080/tcp", new OldServerImpl("myserv1",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32100",
//                                                        new ServerPropertiesImpl(null,
//                                                                CONTAINERINFO_GATEWAY  + ":32100",
//                                                                "http://" + CONTAINERINFO_GATEWAY  + ":32100")));
//        expectedServers.put("100100/udp", new OldServerImpl("myserv2",
//                                                         "dhcp",
//                                                         CONTAINERINFO_GATEWAY  + ":32101",
//                                                         "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/some",
//                                                         new ServerPropertiesImpl("/some",
//                                                                 CONTAINERINFO_GATEWAY  + ":32101",
//                                                                 "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/some")));
//        expectedServers.put("8080/udp", new OldServerImpl("myserv1-tftp",
//                                                       "tftp",
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       "tftp://" + CONTAINERINFO_GATEWAY  + ":32102/some/path",
//                                                        new ServerPropertiesImpl("/some/path",
//                                                                 CONTAINERINFO_GATEWAY  + ":32102",
//                                                                 "tftp://" + CONTAINERINFO_GATEWAY  + ":32102/some/path")));
//        expectedServers.put("8000/tcp", new OldServerImpl("OldServer-8000-tcp",
//                                                       "tcp",
//                                                       CONTAINERINFO_GATEWAY  + ":32103",
//                                                       "tcp://" + CONTAINERINFO_GATEWAY  + ":32103/path",
//                                                       new ServerPropertiesImpl("/path",
//                                                                 CONTAINERINFO_GATEWAY  + ":32103",
//                                                                 "tcp://" + CONTAINERINFO_GATEWAY  + ":32103/path")));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldAllowToUsePortFromMachineConfigWithoutTransportProtocol() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        List<OldServerConfImpl> serversConfigs = new ArrayList<>();
//        doReturn(serversConfigs).when(machineConfig).getServers();
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        serversConfigs.add(new OldServerConfImpl("myserv1", "8080", "http", "/some"));
//        serversConfigs.add(new OldServerConfImpl("myserv1-tftp", "8080/udp", "tftp", "path"));
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                Collections.emptySet(),
//                                                Collections.emptySet());
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("8080/tcp", new OldServerImpl("myserv1",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY + ":32100/some",
//                                                       new ServerPropertiesImpl("/some",
//                                                               CONTAINERINFO_GATEWAY + ":32100",
//                                                               "http://" + CONTAINERINFO_GATEWAY + ":32100/some")));
//        expectedServers.put("8080/udp", new OldServerImpl("myserv1-tftp",
//                                                       "tftp",
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       "tftp://" + CONTAINERINFO_GATEWAY  + ":32102/path",
//                                                       new ServerPropertiesImpl("path",
//                                                               CONTAINERINFO_GATEWAY  + ":32102",
//                                                               "tftp://" + CONTAINERINFO_GATEWAY  + ":32102/path")));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldAddRefUrlPathToServerFromLabels() throws Exception {
//        // given
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                Collections.emptySet(),
//                                                Collections.emptySet());
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        Map<String, String> labels = new HashMap<>();
//        when(containerConfig.getLabels()).thenReturn(labels);
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("100100/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                           .withHostPort("32101")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        ports.put("8000/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32103")));
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8080/tcp"), "myserv1");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8080/tcp"), "http");
//        labels.put(String.format(SERVER_CONF_LABEL_PATH_KEY, "8080/tcp"), "/some/path");
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8080/udp"), "myserv1-tftp");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8080/udp"), "tftp");
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "100100/udp"), "myserv2");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "100100/udp"), "dhcp");
//        labels.put(String.format(SERVER_CONF_LABEL_PATH_KEY, "100100/udp"), "some/path");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8000/tcp"), "tcp");
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("8080/tcp", new OldServerImpl("myserv1",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32100/some/path",
//                                                       new ServerPropertiesImpl("/some/path",
//                                                                 CONTAINERINFO_GATEWAY  + ":32100",
//                                                                 "http://" + CONTAINERINFO_GATEWAY  + ":32100/some/path")));
//        expectedServers.put("100100/udp", new OldServerImpl("myserv2",
//                                                         "dhcp",
//                                                         CONTAINERINFO_GATEWAY  + ":32101",
//                                                         "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/some/path",
//                                                         new ServerPropertiesImpl("some/path",
//                                                                 CONTAINERINFO_GATEWAY  + ":32101",
//                                                                 "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/some/path")));
//        expectedServers.put("8080/udp", new OldServerImpl("myserv1-tftp",
//                                                       "tftp",
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       "tftp://" + CONTAINERINFO_GATEWAY  + ":32102",
//                                                       new ServerPropertiesImpl(null,
//                                                               CONTAINERINFO_GATEWAY  + ":32102",
//                                                               "tftp://" + CONTAINERINFO_GATEWAY  + ":32102")));
//        expectedServers.put("8000/tcp", new OldServerImpl("OldServer-8000-tcp",
//                                                       "tcp",
//                                                       CONTAINERINFO_GATEWAY  + ":32103",
//                                                       "tcp://" + CONTAINERINFO_GATEWAY  + ":32103",
//                                                       new ServerPropertiesImpl(null,
//                                                               CONTAINERINFO_GATEWAY  + ":32103",
//                                                               "tcp://" + CONTAINERINFO_GATEWAY  + ":32103")));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldAllowToUsePortFromDockerLabelsWithoutTransportProtocol() throws Exception {
//        // given
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                Collections.emptySet(),
//                                                Collections.emptySet());
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        Map<String, String> labels = new HashMap<>();
//        when(containerConfig.getLabels()).thenReturn(labels);
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        ports.put("8000/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32103")));
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8080"), "myserv1");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8080"), "http");
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8080/udp"), "myserv1-tftp");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8080/udp"), "tftp");
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8000"), "myserv2");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8000/tcp"), "tcp");
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("8080/tcp", new OldServerImpl("myserv1",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32100",
//                                                       new ServerPropertiesImpl(null,
//                                                               CONTAINERINFO_GATEWAY  + ":32100",
//                                                               "http://" + CONTAINERINFO_GATEWAY  + ":32100")));
//        expectedServers.put("8080/udp", new OldServerImpl("myserv1-tftp",
//                                                       "tftp",
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       "tftp://" + CONTAINERINFO_GATEWAY  + ":32102",
//                                                       new ServerPropertiesImpl(null,
//                                                               CONTAINERINFO_GATEWAY  + ":32102",
//                                                               "tftp://" + CONTAINERINFO_GATEWAY  + ":32102")));
//        expectedServers.put("8000/tcp", new OldServerImpl("myserv2",
//                                                       "tcp",
//                                                       CONTAINERINFO_GATEWAY  + ":32103",
//                                                       "tcp://" + CONTAINERINFO_GATEWAY  + ":32103",
//                                                       new ServerPropertiesImpl(null,
//                                                               CONTAINERINFO_GATEWAY  + ":32103",
//                                                               "tcp://" + CONTAINERINFO_GATEWAY  + ":32103")));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldPreferMachineConfOverDockerLabels() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        Map<String, String> labels = new HashMap<>();
//        when(containerConfig.getLabels()).thenReturn(labels);
//        List<OldServerConfImpl> serversConfigs = new ArrayList<>();
//        doReturn(serversConfigs).when(machineConfig).getServers();
//        ports.put("8080/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("100100/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                           .withHostPort("32101")));
//        ports.put("8080/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8080/tcp"), "myserv1label");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8080/tcp"), "https");
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "8080/udp"), "myserv1-tftp");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "8080/udp"), "tftp");
//        labels.put(String.format(SERVER_CONF_LABEL_REF_KEY, "100100/udp"), "myserv2label");
//        labels.put(String.format(SERVER_CONF_LABEL_PROTOCOL_KEY, "100100/udp"), "dhcp");
//        labels.put(String.format(SERVER_CONF_LABEL_PATH_KEY, "100100/udp"), "/path");
//        serversConfigs.add(new OldServerConfImpl("myserv1conf", "8080/tcp", "http", null));
//        serversConfigs.add(new OldServerConfImpl(null, "8080/udp", null, "some/path"));
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                Collections.emptySet(),
//                                                Collections.emptySet());
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("8080/tcp", new OldServerImpl("myserv1conf",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32100",
//                                                       new ServerPropertiesImpl(null,
//                                                               CONTAINERINFO_GATEWAY  + ":32100",
//                                                               "http://" + CONTAINERINFO_GATEWAY  + ":32100")));
//        expectedServers.put("100100/udp", new OldServerImpl("myserv2label",
//                                                         "dhcp",
//                                                         CONTAINERINFO_GATEWAY  + ":32101",
//                                                         "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/path",
//                                                         new ServerPropertiesImpl("/path",
//                                                                CONTAINERINFO_GATEWAY  + ":32101",
//                                                                "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/path")));
//        expectedServers.put("8080/udp", new OldServerImpl("OldServer-8080-udp",
//                                                       null,
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       null,
//                                                       new ServerPropertiesImpl("some/path", CONTAINERINFO_GATEWAY  + ":32102", null)));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldAddOnlyCommonSystemServersConfigToNonDevMachine() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        ports.put("4301/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("4302/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32101")));
//        ports.put("4301/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        // add user defined server
//        ports.put("4305/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32103")));
//        Set<OldServerConf> commonSystemServersConfigs = new HashSet<>();
//        commonSystemServersConfigs.add(new OldServerConfImpl("sysServer1-tcp", "4301/tcp", "http", "/some/path"));
//        commonSystemServersConfigs.add(new OldServerConfImpl("sysServer2-udp", "4302/udp", "dhcp", null));
//        commonSystemServersConfigs.add(new OldServerConfImpl("sysServer1-udp", "4301/udp", null, "some/path"));
//        Set<OldServerConf> devSystemServersConfigs = new HashSet<>();
//        devSystemServersConfigs.add(new OldServerConfImpl("devSysServer1-tcp", "4305/tcp", "http", null));
//        when(machineConfig.isDev()).thenReturn(false);
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                devSystemServersConfigs,
//                                                commonSystemServersConfigs);
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("4301/tcp", new OldServerImpl("sysServer1-tcp",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32100/some/path",
//                                                       new ServerPropertiesImpl("/some/path",
//                                                                   CONTAINERINFO_GATEWAY  + ":32100",
//                                                                   "http://" + CONTAINERINFO_GATEWAY  + ":32100/some/path")));
//        expectedServers.put("4302/udp", new OldServerImpl("sysServer2-udp",
//                                                       "dhcp",
//                                                       CONTAINERINFO_GATEWAY  + ":32101",
//                                                       "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101",
//                                                       new ServerPropertiesImpl(null,
//                                                                    CONTAINERINFO_GATEWAY  + ":32101",
//                                                                    "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101")));
//        expectedServers.put("4301/udp", new OldServerImpl("sysServer1-udp",
//                                                       null,
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       null,
//                                                       new ServerPropertiesImpl("some/path",
//                                                                   CONTAINERINFO_GATEWAY  + ":32102",
//                                                                   null)));
//        expectedServers.put("4305/tcp", new OldServerImpl("OldServer-4305-tcp",
//                                                       null,
//                                                       CONTAINERINFO_GATEWAY  + ":32103",
//                                                       null,
//                                                       new ServerPropertiesImpl(null, CONTAINERINFO_GATEWAY  + ":32103", null)));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
//
//    @Test
//    public void shouldAddCommonAndDevSystemServersConfigToDevMachine() throws Exception {
//        // given
//        Map<String, List<PortBinding>> ports = new HashMap<>();
//        when(networkSettings.getPorts()).thenReturn(ports);
//        ports.put("4301/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32100")));
//        ports.put("4302/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32101")));
//        ports.put("4305/tcp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32102")));
//        ports.put("4305/udp", Collections.singletonList(new PortBinding().withHostIp(ALL_IP_ADDRESS )
//                                                                         .withHostPort("32103")));
//        Set<OldServerConf> commonSystemServersConfigs = new HashSet<>();
//        commonSystemServersConfigs.add(new OldServerConfImpl("sysServer1-tcp", "4301/tcp", "http", "/some/path1"));
//        commonSystemServersConfigs.add(new OldServerConfImpl("sysServer2-udp", "4302/udp", "dhcp", "some/path2"));
//        Set<OldServerConf> devSystemServersConfigs = new HashSet<>();
//        devSystemServersConfigs.add(new OldServerConfImpl("devSysServer1-tcp", "4305/tcp", "http", "/some/path3"));
//        devSystemServersConfigs.add(new OldServerConfImpl("devSysServer1-udp", "4305/udp", null, "some/path4"));
//        when(machineConfig.isDev()).thenReturn(true);
//        runtimeInfo = new DockerInstanceRuntime(containerInfo,
//                                                machineConfig,
//                                                DEFAULT_HOSTNAME,
//                                                provider,
//                                                devSystemServersConfigs,
//                                                commonSystemServersConfigs);
//        final HashMap<String, OldServerImpl> expectedServers = new HashMap<>();
//        expectedServers.put("4301/tcp", new OldServerImpl("sysServer1-tcp",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32100",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32100/some/path1",
//                                                       new ServerPropertiesImpl("/some/path1",
//                                                           CONTAINERINFO_GATEWAY  + ":32100",
//                                                           "http://" + CONTAINERINFO_GATEWAY  + ":32100/some/path1")));
//        expectedServers.put("4302/udp", new OldServerImpl("sysServer2-udp",
//                                                       "dhcp",
//                                                       CONTAINERINFO_GATEWAY  + ":32101",
//                                                       "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/some/path2",
//                                                       new ServerPropertiesImpl("some/path2",
//                                                           CONTAINERINFO_GATEWAY  + ":32101",
//                                                           "dhcp://" + CONTAINERINFO_GATEWAY  + ":32101/some/path2")));
//        expectedServers.put("4305/tcp", new OldServerImpl("devSysServer1-tcp",
//                                                       "http",
//                                                       CONTAINERINFO_GATEWAY  + ":32102",
//                                                       "http://" + CONTAINERINFO_GATEWAY  + ":32102/some/path3",
//                                                       new ServerPropertiesImpl("/some/path3",
//                                                           CONTAINERINFO_GATEWAY  + ":32102",
//                                                           "http://" + CONTAINERINFO_GATEWAY  + ":32102/some/path3")));
//        expectedServers.put("4305/udp", new OldServerImpl("devSysServer1-udp",
//                                                       null,
//                                                       CONTAINERINFO_GATEWAY  + ":32103",
//                                                       null,
//                                                       new ServerPropertiesImpl("some/path4",
//                                                           CONTAINERINFO_GATEWAY  + ":32103",
//                                                           null)));
//
//        // when
//        final Map<String, OldServerImpl> servers = runtimeInfo.getServers();
//
//        // then
//        assertEquals(servers, expectedServers);
//    }
}
