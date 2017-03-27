/*******************************************************************************
 * Copyright (c) 2012-2017 Codenvy, S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Codenvy, S.A. - initial API and implementation
 *******************************************************************************/
package org.eclipse.che.ide.extension.machine.client.perspective.widgets.machine.appliance.sufficientinfo;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Dmitry Shnurenko
 */
@RunWith(MockitoJUnitRunner.class)
public class MachineInfoPresenterTest {

//    private static final String SOME_TEXT = "someText";
//
//    //constructor mocks
//    @Mock
//    private MachineInfoView          view;
//    @Mock
//    private UserProfileServiceClient userProfile;
//    @Mock
//    private WorkspaceServiceClient   wsService;
//    @Mock
//    private DtoUnmarshallerFactory   unmarshallerFactory;
//
//    //additional mocks
//    @Mock
//    private MachineEntity                machine;
//    @Mock
//    private AcceptsOneWidget             container;
//    @Mock
//    private Unmarshallable<ProfileDto>   profileUnmarshaller;
//    @Mock
//    private Unmarshallable<WorkspaceDto> wsUnmarshaller;
//    @Mock
//    private ProfileDto                   profileDescriptor;
//    @Mock
//    private WorkspaceDto                 wsDescriptor;
//    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//    private Promise<WorkspaceDto>        promise;
//
//    @Captor
//    private ArgumentCaptor<AsyncRequestCallback<ProfileDto>>   profileCaptor;
//    @Captor
//    private ArgumentCaptor<AsyncRequestCallback<WorkspaceDto>> wsCaptor;
//
//    @InjectMocks
//    private MachineInfoPresenter presenter;
//
//    @Before
//    public void setUp() {
//        when(machine.getWorkspaceId()).thenReturn(SOME_TEXT);
//
//        when(unmarshallerFactory.newUnmarshaller(ProfileDto.class)).thenReturn(profileUnmarshaller);
//        when(unmarshallerFactory.newUnmarshaller(WorkspaceDto.class)).thenReturn(wsUnmarshaller);
//    }
//
//    @Test
//    public void infoShouldBeUpdated() {
//        when(wsService.getWorkspace(SOME_TEXT)).thenReturn(promise);
//
//        presenter.update(machine);
//
//        verify(unmarshallerFactory).newUnmarshaller(ProfileDto.class);
//
//        verify(userProfile).getCurrentProfile(Matchers.<AsyncRequestCallback<ProfileDto>>anyObject());
//        verify(machine).getWorkspaceId();
//        verify(wsService).getWorkspace(eq(SOME_TEXT));
//
//        verify(view).updateInfo(machine);
//    }
//
//    @Test
//    public void ownerNameShouldBeSetWhenThereAreFirstAndLastNames() throws Exception {
//        Map<String, String> attributes = new HashMap<>();
//        attributes.put(MachineInfoPresenter.FIRST_NAME_KEY, "firstName");
//        attributes.put(MachineInfoPresenter.LAST_NAME_KEY, "lastName");
//
//        when(profileDescriptor.getAttributes()).thenReturn(attributes);
//        when(wsService.getWorkspace(SOME_TEXT)).thenReturn(promise);
//
//        presenter.update(machine);
//
//        verify(userProfile).getCurrentProfile(profileCaptor.capture());
//        AsyncRequestCallback<ProfileDto> callback = profileCaptor.getValue();
//
//        //noinspection NonJREEmulationClassesInClientCode
//        Method method = callback.getClass().getDeclaredMethod("onSuccess", Object.class);
//
//        method.setAccessible(true);
//
//        method.invoke(callback, profileDescriptor);
//
//        verify(view).setOwner("firstName lastName");
//    }
//
//    @Test
//    public void ownerEmailShouldBeSetWhenThereAreNotFirstOrLastName() throws Exception {
//        Map<String, String> attributes = new HashMap<>();
//        attributes.put(MachineInfoPresenter.FIRST_NAME_KEY, "undefined");
//        attributes.put(MachineInfoPresenter.LAST_NAME_KEY, "<none>");
//        attributes.put(MachineInfoPresenter.EMAIL_KEY, "email");
//
//        when(profileDescriptor.getAttributes()).thenReturn(attributes);
//        when(wsService.getWorkspace(SOME_TEXT)).thenReturn(promise);
//
//        presenter.update(machine);
//
//        verify(userProfile).getCurrentProfile(profileCaptor.capture());
//        AsyncRequestCallback<ProfileDto> callback = profileCaptor.getValue();
//
//        //noinspection NonJREEmulationClassesInClientCode
//        Method method = callback.getClass().getDeclaredMethod("onSuccess", Object.class);
//
//        method.setAccessible(true);
//
//        method.invoke(callback, profileDescriptor);
//
//        verify(view).setOwner("email");
//    }
//
//    @Test
//    //TODO fix test
//    public void workspaceNameShouldBeSet() throws Exception {
//        when(machine.getWorkspaceId()).thenReturn(SOME_TEXT);
//        WorkspaceConfigDto wsConfigDto = mock(WorkspaceConfigDto.class);
//        when(wsDescriptor.getConfig()).thenReturn(wsConfigDto);
//        when(wsConfigDto.getName()).thenReturn(SOME_TEXT);
//        when(wsService.getWorkspace(SOME_TEXT)).thenReturn(promise);
//
//        presenter.update(machine);
//
//        verify(wsService).getWorkspace(eq(SOME_TEXT));
//
//        verify(machine).getWorkspaceId();
////        verify(wsDescriptor).getName();
////        verify(view).setWorkspaceName(SOME_TEXT);
//    }
//
//    @Test
//    public void terminalShouldBeDisplayed() {
//        presenter.go(container);
//
//        verify(container).setWidget(view);
//    }
//
//    @Test
//    public void terminalVisibilityShouldBeChanged() {
//        presenter.setVisible(true);
//
//        verify(view).setVisible(true);
//    }
}
